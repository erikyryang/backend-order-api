package com.marketplace.backend.security;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TenantEntityRepository extends JpaRepository<TenantEntity, Long> {

//    boolean existsByEntityIdAndEntityTypeAndTenantId(Long entityId, String entityType, Long tenantId);
}