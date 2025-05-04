package com.marketplace.backend.order.repository;

import com.marketplace.backend.order.entity.WaiterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WaiterRepository extends JpaRepository<WaiterEntity, Long> {
    Optional<WaiterEntity> findByEmail(String email);
    Optional<WaiterEntity> findByEmployeeId(String employeeId);
}
