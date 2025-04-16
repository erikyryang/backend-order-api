package com.marketplace.backend.order.repository;

import com.marketplace.backend.order.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AddressRepository  extends JpaRepository<AddressEntity, UUID> {
}
