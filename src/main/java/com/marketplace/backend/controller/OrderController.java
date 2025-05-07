package com.marketplace.backend.controller;

import com.marketplace.backend.domain.order.OrderDTO;
import com.marketplace.backend.domain.order.UpdateOrderStatusDTO;
import com.marketplace.backend.domain.order.OrderEntity;
import com.marketplace.backend.domain.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    public final OrderService orderService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody OrderDTO orderDTO) {
        return ResponseEntity.ok(orderService.create(orderDTO));
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderEntity> getById(@PathVariable("id") String id) {
        Double idConverted = Double.valueOf(id);
        OrderEntity order = orderService.findByOrderId(idConverted).getFirst();
        return ResponseEntity.ok(order);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<OrderEntity>> getAll() {
        List<OrderEntity> orders = orderService.findAll();
        return ResponseEntity.ok(orders);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody OrderDTO orderDTO) {
        try {
            Double idConverted = Double.valueOf(id);
            OrderEntity updated = orderService.update(idConverted, orderDTO);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Invalid request");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        try {
            Double idConverted = Double.valueOf(id);
            orderService.deleteLogicallyByUuid(idConverted);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Invalid request");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @PutMapping("/update/status/{id}")
    public ResponseEntity<?> updateStatus(@PathVariable String id, @RequestBody UpdateOrderStatusDTO statusOrderDTO) {
        try {
            Double idConverted = Double.valueOf(id);
            OrderEntity updated = orderService
                    .updateStatus(idConverted, statusOrderDTO);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Invalid request");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
}
