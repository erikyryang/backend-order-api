package com.marketplace.backend.controller;

import com.marketplace.backend.domain.customer.CustomerService;
import com.marketplace.backend.domain.customer.LoginDTO;
import com.marketplace.backend.domain.customer.entity.CustomerEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Customer Management", description = "APIs for managing customers")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @Operation(summary = "Register a new customer", description = "Creates a new customer with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer registered successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerEntity.class))),
            @ApiResponse(responseCode = "400", description = "Invalid customer data provided", content = @Content)
    })
    @PostMapping("/register")
    public ResponseEntity<CustomerEntity> create(@RequestBody CustomerEntity customer) {
        CustomerEntity createdCustomer = customerService.create(customer);
        return ResponseEntity.ok(createdCustomer);
    }

    @Operation(summary = "Retrieve all customers", description = "Fetches a list of all registered customers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customers retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerEntity.class)))
    })
    @GetMapping
    public ResponseEntity<List<CustomerEntity>> getAllCustomers() {
        List<CustomerEntity> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    @Operation(summary = "Retrieve a customer by UUID", description = "Fetches a customer using their unique UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerEntity.class))),
            @ApiResponse(responseCode = "404", description = "Customer not found", content = @Content)
    })
    @GetMapping("/{uuid}")
    public ResponseEntity<CustomerEntity> getById(
            @Parameter(description = "UUID of the customer", required = true) @PathVariable String uuid) {
        UUID uuidConverted = UUID.fromString(uuid);
        CustomerEntity customer = customerService.getByUuid(uuidConverted);
        return ResponseEntity.ok(customer);
    }

    @Operation(summary = "Update a customer", description = "Updates an existing customer identified by their UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerEntity.class))),
            @ApiResponse(responseCode = "404", description = "Customer not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid customer data provided", content = @Content)
    })
    @PutMapping("/{uuid}")
    public ResponseEntity<CustomerEntity> update(
            @Parameter(description = "UUID of the customer", required = true) @PathVariable String uuid,
            @RequestBody CustomerEntity userDetails) {
        UUID userUuid = UUID.fromString(uuid);
        CustomerEntity updatedUser = customerService.update(userUuid, userDetails);
        return ResponseEntity.ok(updatedUser);
    }

    @Operation(summary = "Delete a customer", description = "Deletes a customer identified by their UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Customer deleted successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Customer not found", content = @Content)
    })
    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "UUID of the customer", required = true) @PathVariable String uuid) {
        UUID userUuid = UUID.fromString(uuid);
        customerService.delete(userUuid);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Validate customer password", description = "Validates the password for a customer identified by their UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password validation result",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class))),
            @ApiResponse(responseCode = "404", description = "Customer not found", content = @Content)
    })
    @PostMapping("/{uuid}/validate-password")
    public ResponseEntity<Boolean> validatePassword(
            @Parameter(description = "UUID of the customer", required = true) @PathVariable String uuid,
            @RequestBody String password) {
        UUID uuidConverted = UUID.fromString(uuid);
        boolean isValid = customerService.validatePassword(uuidConverted, password);
        return ResponseEntity.ok(isValid);
    }

    @Operation(summary = "Customer login", description = "Authenticates a customer using email and password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer authenticated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerEntity.class))),
            @ApiResponse(responseCode = "401", description = "Invalid credentials", content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<CustomerEntity> login(@RequestBody LoginDTO loginRequest) {
        CustomerEntity customer = customerService.login(loginRequest.getEmail(), loginRequest.getPassword());
        return ResponseEntity.ok(customer);
    }
}