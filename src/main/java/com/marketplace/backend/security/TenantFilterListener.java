package com.marketplace.backend.security;

import com.marketplace.backend.domain.user.BaseUserEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.PostPersist;

import java.util.UUID;


@Component
public class TenantFilterListener {

    @PersistenceContext
    private EntityManager entityManager;

    @PrePersist
    public void prePersist(Object object) {
        UUID tenantId = TenantContext.getTenantId();
        if (tenantId != null && object instanceof BaseUserEntity userEntity) {
            userEntity.setTenantId(tenantId);
        }
    }

    @PreUpdate
    @PostPersist
    @Transactional
    public void beforeAnyOperation(Object object) {
        UUID tenantId = TenantContext.getTenantId();
        if (tenantId != null) {
            Session session = entityManager.unwrap(Session.class);
            session.enableFilter("tenantFilter").setParameter("tenantId", tenantId);
            session.enableFilter("productTenantFilter").setParameter("tenantId", tenantId);
        }
    }
}