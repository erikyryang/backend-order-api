package com.marketplace.backend.controller;

import com.marketplace.backend.domain.customer.LoginDTO;
import com.marketplace.backend.domain.customer.entity.CustomerEntity;
import com.marketplace.backend.domain.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<CustomerEntity> create(@RequestBody CustomerEntity customer) {
        CustomerEntity createdCustomer = customerService.create(customer);
        return ResponseEntity.ok(createdCustomer);
    }

    @GetMapping
    public ResponseEntity<List<CustomerEntity>> getAllCustomers() {
        List<CustomerEntity> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<CustomerEntity> getById(@PathVariable String uuid) {
        UUID uuidConverted = UUID.fromString(uuid);
        CustomerEntity customer = customerService.getByUuid(uuidConverted);
        return ResponseEntity.ok(customer);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<CustomerEntity> update(@PathVariable String uuid, @RequestBody CustomerEntity userDetails) {
        UUID userUuid = UUID.fromString(uuid);
        CustomerEntity updatedUser = customerService.update(userUuid, userDetails);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable String uuid) {
        UUID userUuid = UUID.fromString(uuid);
        customerService.delete(userUuid);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{uuid}/validate-password")
    public ResponseEntity<Boolean> validatePassword(@PathVariable String uuid, @RequestBody String password) {
        UUID uuidConveted = UUID.fromString(uuid);
        boolean isValid = customerService.validatePassword(uuidConveted, password);
        return ResponseEntity.ok(isValid);
    }

    @PostMapping("/login")
    public ResponseEntity<CustomerEntity> login(@RequestBody LoginDTO loginRequest) {
        CustomerEntity customer = customerService.login(loginRequest.getEmail(), loginRequest.getPassword());
        return ResponseEntity.ok(customer);
    }
}