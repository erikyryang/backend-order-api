package com.marketplace.backend.domain.order.repository;

import com.marketplace.backend.domain.order.entity.CouponEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CouponRepository extends JpaRepository<CouponEntity, UUID> {
    Optional<CouponEntity> findByCode(String code);
}