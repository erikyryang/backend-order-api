package com.marketplace.backend.domain.customer.repository;

import com.marketplace.backend.domain.customer.entity.CustomerEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, UUID> {
    @Modifying
    @Transactional
    @Query("UPDATE CustomerEntity o SET o.active = false WHERE o.uuid = :uuid AND o.active = true")
    int deleteLogicallyByUuid(UUID uuid);

    Optional<CustomerEntity> findByUuidAndActiveTrue(UUID uuid);

    Optional<CustomerEntity> findByEmailAndActiveTrue(String email);

    List<CustomerEntity> findAllByActiveTrue();
}
