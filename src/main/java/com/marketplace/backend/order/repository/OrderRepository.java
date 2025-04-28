package com.marketplace.backend.order.repository;

import com.marketplace.backend.order.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Double> {

    Optional<OrderEntity> findById(Double orderId);
}
