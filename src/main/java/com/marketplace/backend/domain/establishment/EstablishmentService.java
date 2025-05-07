package com.marketplace.backend.domain.establishment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marketplace.backend.domain.establishment.dto.EstablishmentDTO;
import com.marketplace.backend.domain.establishment.entity.EstablishmentEntity;
import com.marketplace.backend.domain.establishment.repository.EstablishmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EstablishmentService {

    private final EstablishmentRepository establishmentRepository;
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

    public EstablishmentDTO create(EstablishmentDTO establishmentRequest) {
        EstablishmentEntity establishment = objectMapper.convertValue(establishmentRequest, EstablishmentEntity.class);
        if (establishmentRepository.findByEmailAndActiveTrue(establishment.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
        }

        String salt = generateSalt();
        String hashedPassword = hashPassword(establishment.getPassword(), salt);

        establishment.setSalt(salt);
        establishment.setPassword(hashedPassword);

        EstablishmentEntity establishmentResult = establishmentRepository.save(establishment);
        return objectMapper.convertValue(establishmentResult, EstablishmentDTO.class);
    }

    public EstablishmentDTO getByUuid(UUID uuid) {
        EstablishmentEntity establishment = establishmentRepository.findByUuidAndActiveTrue(uuid).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Establishment not found"));
        return objectMapper.convertValue(establishment, EstablishmentDTO.class);
    }

    public EstablishmentDTO update(UUID uuid, EstablishmentDTO establishmentRequest) {
        EstablishmentEntity establishment = objectMapper.convertValue(establishmentRequest, EstablishmentEntity.class);
        EstablishmentEntity currentEstablishment = establishmentRepository.findById(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Establishment not found"));
        currentEstablishment.setName(establishment.getName());
        currentEstablishment.setEmail(establishment.getEmail());
        currentEstablishment.setPhone(establishment.getPhone());
        currentEstablishment.setAddresses(establishment.getAddresses());
        if (establishment.getPassword() != null
                && !establishment.getPassword().equals(currentEstablishment.getPassword())) {
            String salt = generateSalt();
            String hashedPassword = hashPassword(establishment.getPassword(), salt);
            establishment.setSalt(salt);
            establishment.setPassword(hashedPassword);
        }
        EstablishmentEntity establishmentResult = establishmentRepository.save(establishment);
        return objectMapper.convertValue(establishmentResult, EstablishmentDTO.class);
    }

    public void delete(UUID uuid) {
        if (!establishmentRepository.existsById(uuid)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Establishment not found");
        }
        establishmentRepository.deleteLogicallyByUuid(uuid);
    }

    public EstablishmentDTO login(String email, String password) {
        EstablishmentEntity establishment = establishmentRepository.findByEmailAndActiveTrue(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid email or password"));
        if (!BCrypt.checkpw(password + establishment.getSalt(), establishment.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid email or password");
        }
        return objectMapper.convertValue(establishment, EstablishmentDTO.class);
    }
}
