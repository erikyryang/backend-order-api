package com.marketplace.backend.domain.customer;

import com.marketplace.backend.domain.customer.entity.CustomerEntity;
import com.marketplace.backend.domain.customer.repository.CustomerRepository;
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
public class CustomerService {

    private final CustomerRepository customerRepository;

    private static final int SALT_LENGTH = 16;

    private String generateSalt() {
        byte[] salt = new byte[SALT_LENGTH];
        new SecureRandom().nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    private String hashPassword(String password, String salt) {
        return BCrypt.hashpw(password + salt, BCrypt.gensalt());
    }

    public CustomerEntity create(CustomerEntity customer) {
        if (customerRepository.findByEmailAndActiveTrue(customer.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        String salt = generateSalt();
        String hashedPassword = hashPassword(customer.getPassword(), salt);

        customer.setSalt(salt);
        customer.setPassword(hashedPassword);

        return customerRepository.save(customer);
    }

    public List<CustomerEntity> getAllCustomers() {
        return customerRepository.findAllByActiveTrue();
    }

    public CustomerEntity getByUuid(UUID uuid) {
        return customerRepository.findByUuidAndActiveTrue(uuid).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
    }

    public CustomerEntity update(UUID uuid, CustomerEntity customerDetails) {
        CustomerEntity customer = customerRepository.findById(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
        customer.setName(customer.getName());
        customer.setEmail(customer.getEmail());
        customer.setPhone(customer.getPhone());
        customer.setAddresses(customer.getAddresses());
        if (customerDetails.getPassword() != null && !customer.getPassword().equals(customerDetails.getPassword())) {
            String salt = generateSalt();
            String hashedPassword = hashPassword(customer.getPassword(), salt);
            customer.setSalt(salt);
            customer.setPassword(hashedPassword);
        }
        return customerRepository.save(customer);
    }

    public void delete(UUID uuid) {
        if (!customerRepository.existsById(uuid)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        customerRepository.deleteLogicallyByUuid(uuid);
    }

    public boolean validatePassword(UUID uuid, String password) {
        CustomerEntity customer = customerRepository.findByUuidAndActiveTrue(uuid).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        String hashedInputPassword = hashPassword(password, customer.getSalt());
        return hashedInputPassword.equals(customer.getPassword());
    }

    public CustomerEntity login(String email, String password) {
        CustomerEntity customer = customerRepository.findByEmailAndActiveTrue(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid email or password"));

        if (!BCrypt.checkpw(password + customer.getSalt(), customer.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid email or password");
        }
        return customer;
    }

}
