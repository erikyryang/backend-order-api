package com.marketplace.backend.domain.user.waiter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marketplace.backend.domain.user.RoleEnum;
import com.marketplace.backend.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

import static com.marketplace.backend.util.PasswordUtil.hashPassword;

@Service
@RequiredArgsConstructor
public class WaiterService {

    private final WaiterRepository waiterRepository;
    private final ObjectMapper objectMapper;

    public WaiterDTO create(WaiterDTO waiterRequest) {
        WaiterEntity waiter = objectMapper.convertValue(waiterRequest, WaiterEntity.class);
        if (waiterRepository.findByEmailAndActiveTrue(waiter.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
        }

        String salt = PasswordUtil.generateSalt();

        waiter.setSalt(salt);
        waiter.setPassword(hashPassword(waiterRequest.getPassword(), salt));
        waiter.setRole(RoleEnum.WAITER);
        waiter.setActive(true);
        WaiterEntity waiterResult = waiterRepository.save(waiter);
        return objectMapper.convertValue(waiterResult, WaiterDTO.class);
    }

    public List<WaiterDTO> getAllWaiters() {
        List<WaiterEntity> waiters = waiterRepository.findAllByActiveTrue();
        return waiters.stream().map(
                waiter -> objectMapper.convertValue(waiter, WaiterDTO.class)).toList();
    }

    public WaiterDTO getByEmployeeId(String employeeId) {
        WaiterEntity waiter = waiterRepository.findByEmployeeIdAndActiveTrue(employeeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Waiter not found"));;
        return objectMapper.convertValue(waiter, WaiterDTO.class);
    }

    public WaiterDTO getByUuid(UUID uuid) {
        WaiterEntity waiter = waiterRepository.findById(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Waiter not found"));;
        return objectMapper.convertValue(waiter, WaiterDTO.class);
    }

    public WaiterDTO update(UUID uuid, WaiterDTO waiterRequest) {
        WaiterEntity waiter = objectMapper.convertValue(waiterRequest, WaiterEntity.class);
        WaiterEntity currentWaiter = waiterRepository.findById(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Waiter not found"));
        currentWaiter.setName(waiter.getName());
        currentWaiter.setEmail(waiter.getEmail());
        currentWaiter.setPhone(waiter.getPhone());
        currentWaiter.setEmployeeId(waiter.getEmployeeId());
        if (waiter.getPassword() != null
                && !waiter.getPassword().equals(currentWaiter.getPassword())) {
            String salt = PasswordUtil.generateSalt();
            String hashedPassword = hashPassword(waiter.getPassword(), salt);
            waiter.setSalt(salt);
            waiter.setPassword(hashedPassword);
        }
        WaiterEntity waiterResult = waiterRepository.save(waiter);
        return objectMapper.convertValue(waiterResult, WaiterDTO.class);
    }

    public void delete(UUID uuid) {
        if (!waiterRepository.existsById(uuid)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Waiter not found");
        }
        waiterRepository.deleteLogicallyByUuid(uuid);
    }
}