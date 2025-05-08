package com.marketplace.backend.domain.user.waiter;


import com.marketplace.backend.domain.user.customer.entity.CustomerEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface WaiterRepository extends JpaRepository<WaiterEntity, UUID> {
    @Modifying
    @Transactional
    @Query("UPDATE WaiterEntity o SET o.active = false WHERE o.uuid = :uuid AND o.active = true")
    int deleteLogicallyByUuid(UUID uuid);

    Optional<WaiterEntity> findByEmployeeIdAndActiveTrue(String employeeId);

    Optional<WaiterEntity> findByEmailAndActiveTrue(String email);

    List<WaiterEntity> findAllByActiveTrue();

    Optional<WaiterEntity> findByEmailAndTenantIdAndActiveTrue(String email, UUID tenantId);
}
