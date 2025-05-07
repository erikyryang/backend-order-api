package com.marketplace.backend.controller;

import com.marketplace.backend.domain.order.entity.CouponEntity;
import com.marketplace.backend.domain.order.CouponService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Coupon Management", description = "APIs for managing coupons")
@RestController
@RequiredArgsConstructor
@RequestMapping("/coupon")
public class CouponController {

    private final CouponService couponService;

    @Operation(summary = "Create a new coupon", description = "Creates a new coupon with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Coupon created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CouponEntity.class))),
            @ApiResponse(responseCode = "400", description = "Invalid coupon data provided", content = @Content)
    })
    @PostMapping("/create")
    public CouponEntity create(@RequestBody CouponEntity coupon) {
        return couponService.createCoupon(coupon);
    }

    @Operation(summary = "Retrieve a coupon by code", description = "Fetches a coupon using its unique code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Coupon retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CouponEntity.class))),
            @ApiResponse(responseCode = "404", description = "Coupon not found", content = @Content)
    })
    @GetMapping("/{code}")
    public CouponEntity getCoupon(
            @Parameter(description = "Unique code of the coupon", required = true) @PathVariable String code) {
        return couponService.getCoupon(code);
    }

    @Operation(summary = "Update a coupon", description = "Updates an existing coupon identified by its code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Coupon updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CouponEntity.class))),
            @ApiResponse(responseCode = "404", description = "Coupon not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid coupon data provided", content = @Content)
    })
    @PutMapping("/{code}")
    public CouponEntity update(
            @Parameter(description = "Unique code of the coupon", required = true) @PathVariable String code,
            @RequestBody CouponEntity coupon) {
        return couponService.update(code, coupon);
    }

    @Operation(summary = "Deactivate a coupon", description = "Deactivates a coupon identified by its code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Coupon deactivated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CouponEntity.class))),
            @ApiResponse(responseCode = "404", description = "Coupon not found", content = @Content)
    })
    @PatchMapping("/{code}/deactivate")
    public CouponEntity deactivate(
            @Parameter(description = "Unique code of the coupon", required = true) @PathVariable String code) {
        return couponService.deactivateCoupon(code);
    }
}
