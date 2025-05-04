package com.marketplace.backend.domain.product;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository  extends JpaRepository<ProductEntity, UUID> {

    List<ProductEntity> findAllByCategoryUuidAndActiveTrue(UUID categoryUuid);

    List<ProductEntity> findByNameContainingIgnoreCase(String trim);

    Optional<ProductEntity> findByUuid(UUID uuid);

    @Modifying
    @Transactional
    @Query("UPDATE ProductEntity p SET p.active = false WHERE p.uuid = :uuid AND p.active = true")
    void deleteLogicallyByUuid(UUID uuid);

    List<ProductEntity> findAllByActiveTrue();
}
