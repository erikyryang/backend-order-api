package com.marketplace.backend.controller;

import com.marketplace.backend.domain.order.dto.OrderDTO;
import com.marketplace.backend.domain.order.dto.UpdateOrderStatusDTO;
import com.marketplace.backend.domain.order.entity.OrderEntity;
import com.marketplace.backend.domain.order.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Order Management", description = "APIs for managing orders")
@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "Create a new order", description = "Creates a new order with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderEntity.class))),
            @ApiResponse(responseCode = "400", description = "Invalid order data provided", content = @Content)
    })
    @PostMapping
    public ResponseEntity<OrderEntity> create(@RequestBody OrderDTO orderDTO) {
        return ResponseEntity.ok(orderService.create(orderDTO));
    }

    @Operation(summary = "Retrieve an order by ID", description = "Fetches an order using its unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderEntity.class))),
            @ApiResponse(responseCode = "404", description = "Order not found", content = @Content)
    })
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderEntity> getById(
            @Parameter(description = "ID of the order", required = true) @PathVariable("id") String id) {
        Double idConverted = Double.valueOf(id);
        OrderEntity order = orderService.findById(idConverted).getFirst();
        return ResponseEntity.ok(order);
    }

    @Operation(summary = "Retrieve all orders", description = "Fetches a list of all orders")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orders retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderEntity.class)))
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<OrderEntity>> getAll() {
        List<OrderEntity> orders = orderService.findAll();
        return ResponseEntity.ok(orders);
    }

    @Operation(summary = "Update an order", description = "Updates an existing order identified by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderEntity.class))),
            @ApiResponse(responseCode = "404", description = "Order not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid order data provided", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<OrderEntity> update(
            @Parameter(description = "ID of the order", required = true) @PathVariable String id,
            @RequestBody OrderDTO orderDTO) {
        Double idConverted = Double.valueOf(id);
        OrderEntity updated = orderService.update(idConverted, orderDTO);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Delete an order", description = "Logically deletes an order identified by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Order deleted successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Order not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID of the order", required = true) @PathVariable String id) {
        Double idConverted = Double.valueOf(id);
        orderService.deleteLogicallyByUuid(idConverted);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update order status", description = "Updates the status of an order identified by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order status updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderEntity.class))),
            @ApiResponse(responseCode = "404", description = "Order not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid status data provided", content = @Content)
    })
    @PutMapping("/update/status/{id}")
    public ResponseEntity<OrderEntity> updateStatus(
            @Parameter(description = "ID of the order", required = true) @PathVariable String id,
            @RequestBody UpdateOrderStatusDTO statusOrderDTO) {
        Double idConverted = Double.valueOf(id);
        OrderEntity updated = orderService.updateStatus(idConverted, statusOrderDTO);
        return ResponseEntity.ok(updated);
    }
}