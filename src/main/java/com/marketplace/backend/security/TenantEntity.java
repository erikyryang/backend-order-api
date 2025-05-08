package com.marketplace.backend.security;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tenant_tbl")
public class TenantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tenant_id", nullable = false, columnDefinition = "uuid")
    private UUID tenantId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getTenantId() {
        return tenantId;
    }

    public void setTenantId(UUID tenantId) {
        this.tenantId = tenantId;
    }

    // Campos adicionais para compatibilidade com getters/setters anteriores

//    public void setEntityUuid(UUID entityId) {
//        if (id == null) {
//            id = new TenantEntityId();
//        }
//        id.setEntityId(entityId);
//    }
//
//    public String getEntityType() {
//        return id.getEntityType();
//    }
//
//    public void setEntityType(String entityType) {
//        if (id == null) {
//            id = new TenantEntityId();
//        }
//        id.setEntityType(entityType);
//    }
}

//@Embeddable
//class TenantEntityId implements java.io.Serializable {
//    @Column(name = "entity_id")
//    private UUID entityId;
//
//    @Column(name = "entity_type")
//    private String entityType;
//
//    public UUID getEntityId() {
//        return entityId;
//    }
//
//    public void setEntityId(UUID entityId) {
//        this.entityId = entityId;
//    }
//
//    public String getEntityType() {
//        return entityType;
//    }
//
//    public void setEntityType(String entityType) {
//        this.entityType = entityType;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        TenantEntityId that = (TenantEntityId) o;
//        return entityId.equals(that.entityId) && entityType.equals(that.entityType);
//    }
//
//    @Override
//    public int hashCode() {
//        return 31 * entityId.hashCode() + entityType.hashCode();
//    }
//}