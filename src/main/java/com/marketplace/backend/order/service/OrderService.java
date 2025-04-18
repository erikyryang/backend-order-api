package com.marketplace.backend.order.service;

import com.marketplace.backend.order.dto.CreateOrderTicketDTO;
import com.marketplace.backend.order.entity.OrderEntity;
import com.marketplace.backend.order.entity.ProductEntity;
import com.marketplace.backend.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderEntity createOrderByTicket(List<ProductEntity> products, CreateOrderTicketDTO createOrderTicketDTO) {
        OrderEntity order = OrderEntity.builder().products(products)
                .observations(createOrderTicketDTO.getObservations()).build();
        return orderRepository.save(order);
    }

    public OrderEntity updateOrderByTicket(List<ProductEntity> products, UUID orderUuid) {
        OrderEntity order = orderRepository.findById(orderUuid).orElseThrow(() -> new RuntimeException("Order not found"));
        order.setProducts(products);
        return orderRepository.save(order);
    }

    public OrderEntity updateObservationByTicket(String observations, UUID orderUuid) {
        OrderEntity order = orderRepository.findById(orderUuid).orElseThrow(() -> new RuntimeException("Order not found"));
        order.setObservations(observations);
        return orderRepository.save(order);
    }
}
