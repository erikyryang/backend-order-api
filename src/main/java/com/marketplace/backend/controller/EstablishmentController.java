package com.marketplace.backend.controller;

import com.marketplace.backend.domain.user.establishment.dto.LoginDTO;
import com.marketplace.backend.domain.user.establishment.EstablishmentService;
import com.marketplace.backend.domain.user.establishment.dto.EstablishmentDTO;
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

import java.util.UUID;

@Tag(name = "Establishment Management", description = "APIs for managing establishments")
@RestController
@RequestMapping("/establishment")
@RequiredArgsConstructor
public class EstablishmentController {

    private final EstablishmentService establishmentService;

    @Operation(summary = "Register a new establishment", description = "Creates a new establishment with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Establishment registered successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = EstablishmentDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid establishment data provided", content = @Content)
    })
    @PostMapping("/register")
    public ResponseEntity<EstablishmentDTO> create(@RequestBody EstablishmentDTO establishment) {
        EstablishmentDTO createdEstablishment = establishmentService.create(establishment);
        return ResponseEntity.ok(createdEstablishment);
    }

    @Operation(summary = "Retrieve a establishment by UUID", description = "Fetches a establishment using their unique UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Establishment retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = EstablishmentDTO.class))),
            @ApiResponse(responseCode = "404", description = "Establishment not found", content = @Content)
    })
    @GetMapping("/{uuid}")
    public ResponseEntity<EstablishmentDTO> getById(
            @Parameter(description = "UUID of the establishment", required = true) @PathVariable String uuid) {
        UUID uuidConverted = UUID.fromString(uuid);
        EstablishmentDTO establishment = establishmentService.getByUuid(uuidConverted);
        return ResponseEntity.ok(establishment);
    }

    @Operation(summary = "Update a establishment", description = "Updates an existing establishment identified by their UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Establishment updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = EstablishmentDTO.class))),
            @ApiResponse(responseCode = "404", description = "Establishment not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid establishment data provided", content = @Content)
    })
    @PutMapping("/{uuid}")
    public ResponseEntity<EstablishmentDTO> update(
            @Parameter(description = "UUID of the establishment", required = true) @PathVariable String uuid,
            @RequestBody EstablishmentDTO userDetails) {
        UUID uuidConverted = UUID.fromString(uuid);
        EstablishmentDTO updatedUser = establishmentService.update(uuidConverted, userDetails);
        return ResponseEntity.ok(updatedUser);
    }

    @Operation(summary = "Delete a establishment", description = "Deletes a establishment identified by their UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Establishment deleted successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Establishment not found", content = @Content)
    })
    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "UUID of the establishment", required = true) @PathVariable String uuid) {
        UUID uuidConverted = UUID.fromString(uuid);
        establishmentService.delete(uuidConverted);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Establishment login", description = "Authenticates a establishment using email and password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Establishment authenticated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = EstablishmentDTO.class))),
            @ApiResponse(responseCode = "401", description = "Invalid credentials", content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<EstablishmentDTO> login(@RequestBody LoginDTO loginRequest) {
        EstablishmentDTO establishment = establishmentService.login(loginRequest.getEmail(), loginRequest.getPassword());
        return ResponseEntity.ok(establishment);
    }
}