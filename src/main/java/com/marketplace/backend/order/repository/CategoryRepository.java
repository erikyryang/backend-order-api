package com.marketplace.backend.order.repository;

import com.marketplace.backend.order.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository  extends JpaRepository<CategoryEntity, UUID> {

    Optional<CategoryEntity> findByUuid(UUID uuid);
}
