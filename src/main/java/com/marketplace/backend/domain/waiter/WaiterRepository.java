package com.marketplace.backend.domain.waiter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WaiterRepository extends JpaRepository<WaiterEntity, Long> {
    Optional<WaiterEntity> findByEmail(String email);
    Optional<WaiterEntity> findByEmployeeId(String employeeId);
}
