package com.marketplace.backend.domain.order;

import com.marketplace.backend.domain.order.enums.CouponStatus;
import com.marketplace.backend.domain.order.enums.DiscountEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;

    public CouponEntity createCoupon(CouponEntity coupon) {
        if (couponRepository.findByCode(coupon.getCode()).isPresent()) {
            throw new IllegalArgumentException("Codigo ja existente");
        }
        coupon.setStatus(CouponStatus.VALID);
        return couponRepository.save(coupon);
    }

    public CouponEntity getCoupon(String code) {
        CouponEntity coupon = couponRepository.findByCode(code).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Coupon with code " + code + " not found"));
        if (coupon != null) {
            coupon.setStatus(coupon.getStatus());
        }
        return coupon;
    }

    public CouponEntity update(String code, CouponEntity updatedCoupon) {
        CouponEntity existingCoupon = couponRepository.findByCode(code).orElse(null);
        if (existingCoupon != null) {
            existingCoupon.setDiscountType(updatedCoupon.getDiscountType());
            existingCoupon.setDiscountAmount(updatedCoupon.getDiscountAmount());
            existingCoupon.setActive(updatedCoupon.isActive());
            existingCoupon.setExpirationTime(updatedCoupon.getExpirationTime());
            existingCoupon.setStatus(existingCoupon.getStatus());
            return couponRepository.save(existingCoupon);
        }
        return null;
    }

    public CouponEntity deactivateCoupon(String code) {
        CouponEntity coupon = couponRepository.findByCode(code).orElse(null);
        if (coupon != null) {
            coupon.setActive(false);
            coupon.setStatus(coupon.getStatus()); // Update status
            return couponRepository.save(coupon);
        }
        return null;
    }

    private boolean isCouponExpired(String code) {
        CouponEntity coupon = getCoupon(code);
        if (coupon == null) {
            return false;
        }
        boolean isExpired = coupon.getExpirationTime() != null && coupon.getExpirationTime().isBefore(LocalDateTime.now());
        coupon.setStatus(isExpired ? CouponStatus.EXPIRED : CouponStatus.VALID);
        couponRepository.save(coupon);
        return isExpired;
    }

    private boolean isCouponExpired(CouponEntity coupon) {
        if (coupon == null) {
            return false;
        }
        boolean isExpired = coupon.getExpirationTime() != null && coupon.getExpirationTime().isBefore(LocalDateTime.now());
        coupon.setStatus(isExpired ? CouponStatus.EXPIRED : CouponStatus.VALID);
        couponRepository.save(coupon);
        return isExpired;
    }

    private double applyDiscount(double totalAmount, double discountAmount, DiscountEnum discountType) {
        double discountedAmount;
        if (DiscountEnum.PERCENTAGE.equals(discountType)) {
            discountedAmount = totalAmount - (totalAmount * (discountAmount / 100));
        } else if (DiscountEnum.FIXED_VALUE.equals(discountType)) {
            discountedAmount = totalAmount - discountAmount;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid discount type: " + discountType);
        }
        return discountedAmount < 0 ? 0 : discountedAmount;
    }

    public double applyCoupon(String code, double totalAmount) {
        CouponEntity coupon = getCoupon(code);
        if(isCouponExpired(coupon)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Coupon has expired");
        } else {
            return applyDiscount(totalAmount, coupon.getDiscountAmount(), coupon.getDiscountType());
        }
    }

}
