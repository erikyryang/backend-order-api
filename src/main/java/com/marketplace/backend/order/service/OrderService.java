package com.marketplace.backend.order.service;

import com.marketplace.backend.order.dto.CreateOrderTicketDTO;
import com.marketplace.backend.order.entity.OrderEntity;
import com.marketplace.backend.order.entity.ProductEntity;
import com.marketplace.backend.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderEntity createOrderByTicket(List<ProductEntity> products, CreateOrderTicketDTO createOrderTicketDTO) {
        OrderEntity order = OrderEntity.builder().products(products)
                .observations(createOrderTicketDTO.getObservations()).build();
        return orderRepository.save(order);
    }
}
