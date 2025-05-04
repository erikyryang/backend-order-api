package com.marketplace.backend.domain.order;

import com.marketplace.backend.domain.order.enums.CouponStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;

    public CouponEntity createCoupon(CouponEntity coupon) {
        // Check if a coupon with the same code already exists
        if (couponRepository.findByCode(coupon.getCode()).isPresent()) {
            throw new IllegalArgumentException("Codigo ja existente");
        }
        coupon.setStatus(CouponStatus.VALID);
        return couponRepository.save(coupon);
    }

    public CouponEntity getCoupon(String code) {
        CouponEntity coupon = couponRepository.findByCode(code).orElse(null);
        if (coupon != null) {
            coupon.setStatus(coupon.getStatus()); // Update status on retrieval
        }
        return coupon;
    }

    public CouponEntity editCoupon(String code, CouponEntity updatedCoupon) {
        CouponEntity existingCoupon = couponRepository.findByCode(code).orElse(null);
        if (existingCoupon != null) {
            existingCoupon.setDiscountType(updatedCoupon.getDiscountType());
            existingCoupon.setDiscountAmount(updatedCoupon.getDiscountAmount());
            existingCoupon.setActive(updatedCoupon.isActive());
            existingCoupon.setExpirationTime(updatedCoupon.getExpirationTime());
            existingCoupon.setStatus(existingCoupon.getStatus()); // Update status
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
}
