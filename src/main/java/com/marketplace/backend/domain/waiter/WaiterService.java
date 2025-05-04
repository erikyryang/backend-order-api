package com.marketplace.backend.domain.waiter;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WaiterService {

    @Autowired
    private WaiterRepository waiterRepository;

    public WaiterEntity create(WaiterEntity waiter) {
        Optional<WaiterEntity> existingByEmail = waiterRepository.findByEmail(waiter.getEmail());
        if (existingByEmail.isPresent()) {
            throw new IllegalArgumentException("Email já está em uso");
        }

        Optional<WaiterEntity> existingByEmployeeId = waiterRepository.findByEmployeeId(waiter.getEmployeeId());
        if (existingByEmployeeId.isPresent()) {
            throw new IllegalArgumentException("ID de Funcionário já está em uso");
        }

        return waiterRepository.save(waiter);
    }

    public List<WaiterEntity> getAllWaiters() {
        return waiterRepository.findAll();
    }

    public WaiterEntity getWaiterById(Long id) {
        return waiterRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Garçom não encontrado com ID: " + id));
    }

    public WaiterEntity update(Long id, WaiterEntity updatedWaiter) {
        WaiterEntity existingWaiter = getWaiterById(id);

        // Verifica se o novo email ou employeeId já estão em uso por outro garçom
        if (!existingWaiter.getEmail().equals(updatedWaiter.getEmail())) {
            Optional<WaiterEntity> emailCheck = waiterRepository.findByEmail(updatedWaiter.getEmail());
            if (emailCheck.isPresent()) {
                throw new IllegalArgumentException("Email já está em uso");
            }
        }

        if (!existingWaiter.getEmployeeId().equals(updatedWaiter.getEmployeeId())) {
            Optional<WaiterEntity> employeeIdCheck = waiterRepository.findByEmployeeId(updatedWaiter.getEmployeeId());
            if (employeeIdCheck.isPresent()) {
                throw new IllegalArgumentException("ID de Funcionário já está em uso");
            }
        }

        existingWaiter.setName(updatedWaiter.getName());
        existingWaiter.setEmail(updatedWaiter.getEmail());
        existingWaiter.setPhone(updatedWaiter.getPhone());
        existingWaiter.setEmployeeId(updatedWaiter.getEmployeeId());

        return waiterRepository.save(existingWaiter);
    }

    public void delete(Long id) {
        if (!waiterRepository.existsById(id)) {
            throw new EntityNotFoundException("Garçom não encontrado com ID: " + id);
        }
        waiterRepository.deleteById(id);
    }
}
