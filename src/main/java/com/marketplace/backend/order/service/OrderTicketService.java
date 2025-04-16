package com.marketplace.backend.order.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marketplace.backend.order.dto.CreateOrderTicketDTO;
import com.marketplace.backend.order.entity.OrderTicketEntity;
import com.marketplace.backend.order.entity.ProductEntity;
import com.marketplace.backend.order.repository.OrderTicketRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderTicketService {

    private final OrderTicketRepository orderTicketRepository;
    private final OrderService orderService;
    private final ProductService productService;
    private final ObjectMapper objectMapper;

    @Transactional
    public OrderTicketEntity create(CreateOrderTicketDTO createOrderTicketDTO) {

        List<ProductEntity> products = new ArrayList<>();
        createOrderTicketDTO.getProducts().forEach(uuid -> {
            ProductEntity product = productService.findByUuid(UUID.fromString(uuid));
            products.add(product);
        });

        orderService.createOrderByTicket(products, createOrderTicketDTO);
        OrderTicketEntity orderTicket = objectMapper.convertValue(createOrderTicketDTO, OrderTicketEntity.class);
        return orderTicketRepository.save(orderTicket);
    }
}
