package com.marketplace.backend.domain.order;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Double> {

    Optional<OrderEntity> findById(Double orderId);

    List<OrderEntity> findAllByActiveTrue();

    @Modifying
    @Transactional
    @Query("UPDATE OrderEntity o SET o.active = false WHERE o.id = :id AND o.active = true")
    void deleteLogicallyByUuid(Double id);
}
