package com.marketplace.backend.domain.user.customer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marketplace.backend.domain.user.customer.dto.CustomerDTO;
import com.marketplace.backend.domain.user.customer.entity.CustomerEntity;
import com.marketplace.backend.domain.user.customer.repository.CustomerRepository;
import com.marketplace.backend.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final ObjectMapper objectMapper;

    public CustomerDTO create(CustomerDTO customerRequest) {
        CustomerEntity customer = objectMapper.convertValue(customerRequest, CustomerEntity.class);
        if (customerRepository.findByEmailAndActiveTrue(customer.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
        }

        String salt = PasswordUtil.generateSalt();
        String hashedPassword = PasswordUtil.hashPassword(customer.getPassword(), salt);

        customer.setSalt(salt);
        customer.setPassword(hashedPassword);

        CustomerEntity customerResult = customerRepository.save(customer);
        return objectMapper.convertValue(customerResult, CustomerDTO.class);
    }

    public List<CustomerDTO> getAllCustomers() {
        List<CustomerEntity> customers = customerRepository.findAllByActiveTrue();
        return customers.stream().map(
                customer -> objectMapper.convertValue(customer, CustomerDTO.class)).toList();
    }

    public CustomerDTO getByUuid(UUID uuid) {
        CustomerEntity customer = customerRepository.findByUuidAndActiveTrue(uuid).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
        return objectMapper.convertValue(customer, CustomerDTO.class);
    }

    public CustomerDTO update(UUID uuid, CustomerDTO customerRequest) {
        CustomerEntity customer = objectMapper.convertValue(customerRequest, CustomerEntity.class);
        CustomerEntity currentCustomer = customerRepository.findById(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
        currentCustomer.setName(customer.getName());
        currentCustomer.setEmail(customer.getEmail());
        currentCustomer.setPhone(customer.getPhone());
        currentCustomer.setAddresses(customer.getAddresses());
        if (customer.getPassword() != null
                && !customer.getPassword().equals(currentCustomer.getPassword())) {
            String salt = PasswordUtil.generateSalt();
            String hashedPassword = PasswordUtil.hashPassword(customer.getPassword(), salt);
            customer.setSalt(salt);
            customer.setPassword(hashedPassword);
        }
        CustomerEntity customerResult = customerRepository.save(customer);
        return objectMapper.convertValue(customerResult, CustomerDTO.class);
    }

    public void delete(UUID uuid) {
        if (!customerRepository.existsById(uuid)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        customerRepository.deleteLogicallyByUuid(uuid);
    }

    public CustomerDTO login(String email, String password) {
        CustomerEntity customer = customerRepository.findByEmailAndActiveTrue(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid email or password"));
        if (!BCrypt.checkpw(password + customer.getSalt(), customer.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid email or password");
        }
        return objectMapper.convertValue(customer, CustomerDTO.class);
    }
}
