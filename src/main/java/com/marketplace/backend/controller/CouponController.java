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

    @PostMapping("/add")
    public CouponEntity addCoupon(@RequestBody CouponEntity coupon) {
        return couponService.createCoupon(coupon);
    }

    @GetMapping("/{code}")
    public CouponEntity getCoupon(@PathVariable String code) {
        return couponService.getCoupon(code);
    }

    @PutMapping("/{code}")
    public CouponEntity editCoupon(@PathVariable String code, @RequestBody CouponEntity coupon) {
        return couponService.editCoupon(code, coupon);
    }

    @PatchMapping("/{code}/deactivate")
    public CouponEntity deactivateCoupon(@PathVariable String code) {
        return couponService.deactivateCoupon(code);
    }
}
