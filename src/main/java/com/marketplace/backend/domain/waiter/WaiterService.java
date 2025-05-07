package com.marketplace.backend.domain.waiter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import org.springframework.web.server.ResponseStatusException;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WaiterService {

    private final WaiterRepository waiterRepository;
    private final ObjectMapper objectMapper;

    private static final int SALT_LENGTH = 16;

    private String generateSalt() {
        byte[] salt = new byte[SALT_LENGTH];
        new SecureRandom().nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    private String hashPassword(String password, String salt) {
        return BCrypt.hashpw(password + salt, BCrypt.gensalt());
    }

    public WaiterDTO create(WaiterDTO waiterRequest) {
        WaiterEntity waiter = objectMapper.convertValue(waiterRequest, WaiterEntity.class);
        if (waiterRepository.findByEmailAndActiveTrue(waiter.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
        }

        String salt = generateSalt();
        String hashedPassword = hashPassword(waiter.getPassword(), salt);

        waiter.setSalt(salt);
        waiter.setPassword(hashedPassword);

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

    public WaiterDTO update(UUID uuid, WaiterDTO waiterRequest) {
        WaiterEntity establishment = objectMapper.convertValue(waiterRequest, WaiterEntity.class);
        WaiterEntity currentWaiter = waiterRepository.findById(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Waiter not found"));
        currentWaiter.setName(establishment.getName());
        currentWaiter.setEmail(establishment.getEmail());
        currentWaiter.setPhone(establishment.getPhone());
        currentWaiter.setEmployeeId(establishment.getEmployeeId());
        if (establishment.getPassword() != null
                && !establishment.getPassword().equals(currentWaiter.getPassword())) {
            String salt = generateSalt();
            String hashedPassword = hashPassword(establishment.getPassword(), salt);
            establishment.setSalt(salt);
            establishment.setPassword(hashedPassword);
        }
        WaiterEntity establishmentResult = waiterRepository.save(establishment);
        return objectMapper.convertValue(establishmentResult, WaiterDTO.class);
    }

    public void delete(UUID uuid) {
        if (!waiterRepository.existsById(uuid)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Waiter not found");
        }
        waiterRepository.deleteLogicallyByUuid(uuid);
    }

    public WaiterDTO login(String email, String password) {
        WaiterEntity establishment = waiterRepository.findByEmailAndActiveTrue(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid email or password"));
        if (!BCrypt.checkpw(password + establishment.getSalt(), establishment.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid email or password");
        }
        return objectMapper.convertValue(establishment, WaiterDTO.class);
    }
}