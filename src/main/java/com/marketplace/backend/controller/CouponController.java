package com.marketplace.backend.controller;

import com.marketplace.backend.domain.order.CouponEntity;
import com.marketplace.backend.domain.order.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/coupon")
public class CouponController {

    private final CouponService couponService;

    @PostMapping("/create")
    public CouponEntity create(@RequestBody CouponEntity coupon) {
        return couponService.createCoupon(coupon);
    }

    @GetMapping("/{code}")
    public CouponEntity getCoupon(@PathVariable String code) {
        return couponService.getCoupon(code);
    }

    @PutMapping("/{code}")
    public CouponEntity update(@PathVariable String code, @RequestBody CouponEntity coupon) {
        return couponService.update(code, coupon);
    }

    @PatchMapping("/{code}/deactivate")
    public CouponEntity deactivate(@PathVariable String code) {
        return couponService.deactivateCoupon(code);
    }
}
