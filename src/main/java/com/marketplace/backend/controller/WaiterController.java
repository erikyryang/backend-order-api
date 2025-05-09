package com.marketplace.backend.controller;

import com.marketplace.backend.domain.user.waiter.WaiterDTO;
import com.marketplace.backend.domain.user.waiter.WaiterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Waiter Management", description = "APIs for managing waiters")
@RestController
@RequiredArgsConstructor
@RequestMapping("/waiter")
public class WaiterController {

    private final WaiterService waiterService;

    @Operation(summary = "Create a new waiter", description = "Creates a new waiter with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Waiter created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = WaiterDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid waiter data provided", content = @Content)
    })
    @PostMapping
    public ResponseEntity<WaiterDTO> create(@RequestBody WaiterDTO waiter) {
        WaiterDTO createdWaiter = waiterService.create(waiter);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdWaiter);
    }

    @Operation(summary = "Retrieve all waiters", description = "Fetches a list of all registered waiters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Waiters retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = WaiterDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<WaiterDTO>> getAllWaiters() {
        List<WaiterDTO> waiters = waiterService.getAllWaiters();
        return ResponseEntity.ok(waiters);
    }

    @Operation(summary = "Retrieve a waiter by ID", description = "Fetches a waiter using their unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Waiter retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = WaiterDTO.class))),
            @ApiResponse(responseCode = "404", description = "Waiter not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<WaiterDTO> getById(
            @Parameter(description = "EmployeeId of the waiter", required = true) @PathVariable String id) {
        WaiterDTO waiter = waiterService.getByEmployeeId(id);
        return ResponseEntity.ok(waiter);
    }

    @Operation(summary = "Update a waiter", description = "Updates an existing waiter identified by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Waiter updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = WaiterDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid waiter data provided", content = @Content),
            @ApiResponse(responseCode = "404", description = "Waiter not found", content = @Content)
    })
    @PutMapping("/{uuid}")
    public ResponseEntity<WaiterDTO> update(
            @Parameter(description = "UUID of the waiter", required = true) @PathVariable String uuid,
            @Valid @RequestBody WaiterDTO waiter) {
        UUID uuidConverted = UUID.fromString(uuid);
        WaiterDTO updatedWaiter = waiterService.update(uuidConverted, waiter);
        return ResponseEntity.ok(updatedWaiter);
    }

    @Operation(summary = "Delete a waiter", description = "Deletes a waiter identified by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Waiter deleted successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Waiter not found", content = @Content)
    })
    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID of the waiter", required = true) @PathVariable String uuid) {
        UUID uuidConverted = UUID.fromString(uuid);
        waiterService.delete(uuidConverted);
        return ResponseEntity.noContent().build();
    }
}