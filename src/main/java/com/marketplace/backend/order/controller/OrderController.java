package com.marketplace.backend.order.controller;

import com.marketplace.backend.order.dto.CreateOrderTicketDTO;
import com.marketplace.backend.order.entity.OrderEntity;
import com.marketplace.backend.order.service.OrderService;
import com.marketplace.backend.order.service.OrderTicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class OrderController {

    public final OrderTicketService orderTicketService;

    @PostMapping
    public ResponseEntity<?> createTicketOrder(CreateOrderTicketDTO orderTicketDTO) {
        return ResponseEntity.ok(orderTicketService.create(orderTicketDTO));
    }
}
