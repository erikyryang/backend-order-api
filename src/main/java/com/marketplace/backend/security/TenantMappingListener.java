package com.marketplace.backend.security;

import com.marketplace.backend.domain.user.BaseUserEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.PostPersist;
import java.util.UUID;

//@Component
public class TenantMappingListener {

//    @Autowired
//    private TenantEntityRepository tenantEntityRepository;
//
//    @PersistenceContext
//    private EntityManager entityManager;

//    @PostPersist
//    @Transactional
//    public void postPersist(Object object) {
//        if (object instanceof BaseUserEntity entity) {
//            UUID tenantId = TenantContext.getTenantId();
//            if (tenantId != null) {
//                TenantEntity mapping = new TenantEntity();
//                mapping.setEntityUuid(entity.getUuid());
//                mapping.setEntityType(entity.getClass().getSimpleName().replace("Entity", "").toUpperCase());
//                mapping.setTenantId(tenantId);
//                tenantEntityRepository.save(mapping);
//            }
//        }
//    }
}
