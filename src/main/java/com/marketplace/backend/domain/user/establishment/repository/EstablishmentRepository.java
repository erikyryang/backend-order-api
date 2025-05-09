package com.marketplace.backend.domain.user.establishment.repository;

import com.marketplace.backend.domain.user.establishment.entity.EstablishmentEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EstablishmentRepository extends JpaRepository<EstablishmentEntity, UUID> {
    @Modifying
    @Transactional
    @Query("UPDATE EstablishmentEntity o SET o.active = false WHERE o.uuid = :uuid AND o.active = true")
    int deleteLogicallyByUuid(UUID uuid);

    Optional<EstablishmentEntity> findByUuidAndActiveTrue(UUID uuid);

    Optional<EstablishmentEntity> findByEmailAndActiveTrue(String email);

    List<EstablishmentEntity> findAllByActiveTrue();
}
