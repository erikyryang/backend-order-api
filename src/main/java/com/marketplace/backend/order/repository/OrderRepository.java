package com.marketplace.backend.order.repository;

import com.marketplace.backend.order.entity.OrderEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Double> {

    Optional<OrderEntity> findById(Double orderId);

    List<OrderEntity> findAllByActiveTrue();

    @Modifying
    @Transactional
    @Query("UPDATE OrderEntity o SET o.active = false WHERE o.id = :id AND o.active = true")
    void deleteLogicallyByUuid(Double id);
}
