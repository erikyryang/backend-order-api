package com.marketplace.backend.domain.establishment;

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

    private static final int SALT_LENGTH = 16;

    private String generateSalt() {
        byte[] salt = new byte[SALT_LENGTH];
        new SecureRandom().nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    private String hashPassword(String password, String salt) {
        return BCrypt.hashpw(password + salt, BCrypt.gensalt());
    }

    public EstablishmentEntity create(EstablishmentEntity customer) {
        if (establishmentRepository.findByEmailAndActiveTrue(customer.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        String salt = generateSalt();
        String hashedPassword = hashPassword(customer.getPassword(), salt);

        customer.setSalt(salt);
        customer.setPassword(hashedPassword);

        return establishmentRepository.save(customer);
    }

    public List<EstablishmentEntity> getAllEstablishments() {
        return establishmentRepository.findAllByActiveTrue();
    }

    public EstablishmentEntity getByUuid(UUID uuid) {
        return establishmentRepository.findByUuidAndActiveTrue(uuid).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Establishment not found"));
    }

    public EstablishmentEntity update(UUID uuid, EstablishmentEntity establishmentDetails) {
        EstablishmentEntity establishment = establishmentRepository.findById(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Establishment not found"));
        establishment.setName(establishment.getName());
        establishment.setEmail(establishment.getEmail());
        establishment.setPhone(establishment.getPhone());
        establishment.setAddresses(establishment.getAddresses());
        if (establishmentDetails.getPassword() != null && !establishment.getPassword().equals(establishmentDetails.getPassword())) {
            String salt = generateSalt();
            String hashedPassword = hashPassword(establishment.getPassword(), salt);
            establishment.setSalt(salt);
            establishment.setPassword(hashedPassword);
        }
        return establishmentRepository.save(establishment);
    }

    public void delete(UUID uuid) {
        if (!establishmentRepository.existsById(uuid)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        establishmentRepository.deleteLogicallyByUuid(uuid);
    }

    public boolean validatePassword(UUID uuid, String password) {
        EstablishmentEntity customer = establishmentRepository.findByUuidAndActiveTrue(uuid).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        String hashedInputPassword = hashPassword(password, customer.getSalt());
        return hashedInputPassword.equals(customer.getPassword());
    }

    public EstablishmentEntity login(String email, String password) {
        EstablishmentEntity customer = establishmentRepository.findByEmailAndActiveTrue(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid email or password"));

        if (!BCrypt.checkpw(password + customer.getSalt(), customer.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid email or password");
        }
        return customer;
    }

}
