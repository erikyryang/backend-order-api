package com.marketplace.backend.order.controller;

import com.marketplace.backend.order.dto.CreateOrderTicketDTO;
import com.marketplace.backend.order.dto.OrderTicketResponseDTO;
import com.marketplace.backend.order.dto.UpdateOrderTicketDTO;
import com.marketplace.backend.order.service.OrderTicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    public final OrderTicketService orderTicketService;

    @PostMapping
    public ResponseEntity<?> createTicketOrder(CreateOrderTicketDTO orderTicketDTO) {
        return ResponseEntity.ok(orderTicketService.create(orderTicketDTO));
    }

    @PutMapping("/update/{uuid}")
    public ResponseEntity<?> updateOrderTicket(@PathVariable UUID uuid, @RequestBody UpdateOrderTicketDTO updateOrderTicketDTO) {
        try {
            OrderTicketResponseDTO updated = orderTicketService.update(uuid, updateOrderTicketDTO);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Invalid request");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @DeleteMapping("/delete/{uuid}")
    public ResponseEntity<?> deleteOrderTicket(@PathVariable UUID uuid) {
        try {
            orderTicketService.delete(uuid);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Invalid request");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<?> getOrderTicket(@PathVariable UUID uuid) {
        try {
            OrderTicketResponseDTO orderTicket = orderTicketService.findByUuid(uuid);
            return ResponseEntity.ok(orderTicket);
        } catch (IllegalArgumentException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Invalid request");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @GetMapping
    public ResponseEntity<List<OrderTicketResponseDTO>> getAllOrderTickets() {
        List<OrderTicketResponseDTO> orderTickets = orderTicketService.findAll();
        return ResponseEntity.ok(orderTickets);
    }
}
