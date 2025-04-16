package com.marketplace.backend.order.repository;

import com.marketplace.backend.order.entity.ProductEntity;
import com.marketplace.backend.order.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository  extends JpaRepository<UserEntity, UUID> {
}
