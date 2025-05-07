package com.marketplace.backend.controller;

import com.marketplace.backend.domain.order.dto.OrderDTO;
import com.marketplace.backend.domain.order.dto.UpdateOrderStatusDTO;
import com.marketplace.backend.domain.order.entity.OrderEntity;
import com.marketplace.backend.domain.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        OrderEntity order = orderService.findById(idConverted).getFirst();
        return ResponseEntity.ok(order);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<OrderEntity>> getAll() {
        List<OrderEntity> orders = orderService.findAll();
        return ResponseEntity.ok(orders);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody OrderDTO orderDTO) {
        Double idConverted = Double.valueOf(id);
        OrderEntity updated = orderService.update(idConverted, orderDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        Double idConverted = Double.valueOf(id);
        orderService.deleteLogicallyByUuid(idConverted);
        return ResponseEntity.ok().build();

    }

    @PutMapping("/update/status/{id}")
    public ResponseEntity<?> updateStatus(@PathVariable String id, @RequestBody UpdateOrderStatusDTO statusOrderDTO) {
        Double idConverted = Double.valueOf(id);
        OrderEntity updated = orderService
                .updateStatus(idConverted, statusOrderDTO);
        return ResponseEntity.ok(updated);
    }
}
