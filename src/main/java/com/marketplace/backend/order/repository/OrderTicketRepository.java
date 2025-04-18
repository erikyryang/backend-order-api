package com.marketplace.backend.order.repository;

import com.marketplace.backend.order.entity.OrderTicketEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderTicketRepository  extends JpaRepository<OrderTicketEntity, UUID> {
    @Modifying
    @Transactional
    @Query("UPDATE OrderTicketEntity o SET o.active = false WHERE o.uuid = :uuid AND o.active = true")
    int deleteLogicallyByUuid(UUID uuid);

    List<OrderTicketEntity> findAllByActiveTrue();

    OrderTicketEntity findByUuidAndActiveTrue(UUID uuid);
}
