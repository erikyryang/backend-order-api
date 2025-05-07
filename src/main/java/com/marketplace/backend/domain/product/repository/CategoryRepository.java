package com.marketplace.backend.domain.product.repository;

import com.marketplace.backend.domain.product.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository  extends JpaRepository<CategoryEntity, UUID> {

    Optional<CategoryEntity> findByUuid(UUID uuid);

    Optional<CategoryEntity> findByNameContainingIgnoreCase(String name);

    List<CategoryEntity> findAllByActiveTrue();
}
