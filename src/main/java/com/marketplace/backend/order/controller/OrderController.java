package com.marketplace.backend.order.controller;

import com.marketplace.backend.order.dto.CreateOrderDTO;
import com.marketplace.backend.order.dto.OrderTicketResponseDTO;
import com.marketplace.backend.order.dto.ProductCategoryDTO;
import com.marketplace.backend.order.dto.UpdateOrderTicketDTO;
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
import java.util.UUID;

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
    public ResponseEntity<List<OrderEntity>> getByCategory(
            @RequestParam(defaultValue = "true") boolean retrieveAll,
            @RequestParam(required = false) String orderUuid) {

        return ResponseEntity.ok(orderService.findByOrderUuid(retrieveAll, orderUuid));
    }

//    @PutMapping("/update/{uuid}")
//    public ResponseEntity<?> updateOrderTicket(@PathVariable UUID uuid, @RequestBody UpdateOrderTicketDTO updateOrderTicketDTO) {
//        try {
//            OrderTicketResponseDTO updated = orderService.update(uuid, updateOrderTicketDTO);
//            return ResponseEntity.ok(updated);
//        } catch (IllegalArgumentException e) {
//            Map<String, String> errorResponse = new HashMap<>();
//            errorResponse.put("error", "Invalid request");
//            errorResponse.put("message", e.getMessage());
//            return ResponseEntity.badRequest().body(errorResponse);
//        }
//    }
//
//    @DeleteMapping("/delete/{uuid}")
//    public ResponseEntity<?> deleteOrderTicket(@PathVariable UUID uuid) {
//        try {
//            orderService.delete(uuid);
//            return ResponseEntity.ok().build();
//        } catch (IllegalArgumentException e) {
//            Map<String, String> errorResponse = new HashMap<>();
//            errorResponse.put("error", "Invalid request");
//            errorResponse.put("message", e.getMessage());
//            return ResponseEntity.badRequest().body(errorResponse);
//        }
//    }
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
