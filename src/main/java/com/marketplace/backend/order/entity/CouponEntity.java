package com.marketplace.backend.order.entity;

import com.marketplace.backend.order.enums.CouponStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "coupon_tbl")
public class CouponEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Double id;

    private String code;
    private String discountType;
    private double discountAmount;
    private boolean active;
    private LocalDateTime expirationTime;

    @Enumerated(EnumType.STRING)
    private CouponStatus status;

    public CouponStatus getStatus() {
        if (expirationTime != null && expirationTime.isBefore(LocalDateTime.now())) {
            return CouponStatus.EXPIRED;
        }
        return CouponStatus.VALID;
    }
}
