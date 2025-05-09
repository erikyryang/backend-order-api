package com.marketplace.backend.controller;

import com.marketplace.backend.domain.user.AuthService;
import com.marketplace.backend.domain.user.customer.dto.CustomerDTO;
import com.marketplace.backend.domain.user.customer.dto.LoginDTO;
import com.marketplace.backend.domain.user.establishment.dto.EstablishmentDTO;
import com.marketplace.backend.domain.user.waiter.WaiterDTO;
import com.marketplace.backend.configuration.auth.JwtResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auth Controller", description = "APIs for managing users")
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/customer/login")
    public ResponseEntity<JwtResponse> authenticateCustomer(@RequestBody LoginDTO loginRequest) {
        return ResponseEntity.ok(authService.authenticateCustomer(loginRequest));
    }

    @PostMapping("/waiter/login")
    public ResponseEntity<JwtResponse> authenticateWaiter(@RequestBody LoginDTO loginRequest) {
        return ResponseEntity.ok(authService.authenticateWaiter(loginRequest));
    }

    @PostMapping("/establishment/login")
    public ResponseEntity<JwtResponse> authenticateEstablishment(@RequestBody LoginDTO loginRequest) {
        return ResponseEntity.ok(authService.authenticateEstablishment(loginRequest));
    }

    @Operation(summary = "Register a new customer", description = "Creates a new customer with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer registered successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid customer data provided", content = @Content)
    })
    @PostMapping("/customer/register")
    public ResponseEntity<CustomerDTO> registerCustomer(@RequestBody CustomerDTO customerDTO) {
        return ResponseEntity.ok(authService.registerCustomer(customerDTO));
    }

    @Operation(summary = "Create a new waiter", description = "Creates a new waiter with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Waiter created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = WaiterDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid waiter data provided", content = @Content)
    })
    @PostMapping("/waiter/register")
    public ResponseEntity<WaiterDTO> registerWaiter(@RequestBody WaiterDTO waiterDTO) {
        return ResponseEntity.ok(authService.registerWaiter(waiterDTO));
    }

    @Operation(summary = "Register a new establishment", description = "Creates a new establishment with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Establishment registered successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = EstablishmentDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid establishment data provided", content = @Content)
    })
    @PostMapping("/establishment/register")
    public ResponseEntity<EstablishmentDTO> registerEstablishment(@RequestBody EstablishmentDTO registerRequest) {
        return ResponseEntity.ok(authService.registerEstablishment(registerRequest));
    }
}