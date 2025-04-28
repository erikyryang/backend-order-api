package com.marketplace.backend.order.controller;

import com.marketplace.backend.order.dto.*;
import com.marketplace.backend.order.entity.OrderEntity;
import com.marketplace.backend.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    public final OrderService orderService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateOrderDTO createOrderDTO) {
        return ResponseEntity.ok(orderService.create(createOrderDTO));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<OrderEntity>> getOrders(
            @RequestParam(defaultValue = "true") boolean retrieveAll,
            @RequestParam(required = false) String orderId) {

        return ResponseEntity.ok(orderService.findByOrderId(retrieveAll, orderId));
    }

    @GetMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderEntity> getOrder(
            @RequestParam(defaultValue = "false") boolean retrieveAll,
            @PathVariable String id) {

        OrderEntity order = orderService.findByOrderId(retrieveAll, id).getFirst();
        return ResponseEntity.ok(order);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateOrderTicket(@PathVariable String id, @RequestBody UpdateOrderDTO updateOrderDTO) {
        try {
            Double idConverted = Double.valueOf(id);
            OrderEntity updated = orderService.update(idConverted, updateOrderDTO);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Invalid request");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderTicket(@PathVariable String id) {
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
//
//    @GetMapping("/{uuid}")
//    public ResponseEntity<?> getOrderTicket(@PathVariable UUID uuid) {
//        try {
//            OrderTicketResponseDTO orderTicket = orderService.findByUuid(uuid);
//            return ResponseEntity.ok(orderTicket);
//        } catch (IllegalArgumentException e) {
//            Map<String, String> errorResponse = new HashMap<>();
//            errorResponse.put("error", "Invalid request");
//            errorResponse.put("message", e.getMessage());
//            return ResponseEntity.badRequest().body(errorResponse);
//        }
//    }
//
//    @GetMapping
//    public ResponseEntity<List<OrderTicketResponseDTO>> getAllOrderTickets() {
//        List<OrderTicketResponseDTO> orderTickets = orderService.findAll();
//        return ResponseEntity.ok(orderTickets);
//    }
}
