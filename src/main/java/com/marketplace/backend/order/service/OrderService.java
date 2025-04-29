package com.marketplace.backend.order.service;

import com.marketplace.backend.order.dto.CreateOrderDTO;
import com.marketplace.backend.order.dto.UpdateOrderDTO;
import com.marketplace.backend.order.entity.OrderEntity;
import com.marketplace.backend.order.entity.ProductEntity;
import com.marketplace.backend.order.enums.OrderStatus;
import com.marketplace.backend.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.marketplace.backend.order.OrderUtil.calculateItemsTotal;
import static com.marketplace.backend.order.OrderUtil.convertProductsToOrderItem;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;

    public OrderEntity create(CreateOrderDTO createOrderDTO) {
        List<ProductEntity> products = new ArrayList<>();
        createOrderDTO.getItems().forEach(item -> {
            ProductEntity product = productService.findByUuid(UUID.fromString(item.getUuid()));
            products.add(product);
        });

        OrderEntity order = OrderEntity.builder()
                .tableName(createOrderDTO.getTableName())
                .itens(convertProductsToOrderItem(products))
                .observations(createOrderDTO.getObservations())
                .active(true)
                .paymentMethod(createOrderDTO.getPaymentMethod())
                .status(OrderStatus.PENDING)
                .total(calculateItemsTotal(products))
                .build();
        return orderRepository.save(order);
    }

    public OrderEntity update(Double id, UpdateOrderDTO updateOrderDTO) {
        OrderEntity order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        List<ProductEntity> products = new ArrayList<>();

        updateOrderDTO.getItems().forEach(item -> {
            ProductEntity product = productService.findByUuid(UUID.fromString(item.getUuid()));
            products.add(product);
        });
        order.setItens(convertProductsToOrderItem(products));

        if(updateOrderDTO.getPaymentMethod() != null){
            order.setPaymentMethod(updateOrderDTO.getPaymentMethod());
        }

        if(updateOrderDTO.getTableName() != null){
            order.setTableName(updateOrderDTO.getTableName());
        }

        if(updateOrderDTO.getObservations() != null){
            order.setObservations(updateOrderDTO.getObservations());
        }

        return orderRepository.save(order);
    }

    public List<OrderEntity> findByOrderId(boolean retrieveAll, String orderId) {
        if (retrieveAll) {
            return orderRepository.findAllByActiveTrue();
        }

        if (orderId == null || orderId.trim().isEmpty()) {
            throw new IllegalArgumentException("Order UUID cannot be null or empty");
        }

        try {
            Double id = Double.parseDouble(orderId);
            OrderEntity order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
            return List.of(order);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid UUID format: " + orderId, e);
        }
    }

    public List<OrderEntity> findAll() {
        return orderRepository.findAllByActiveTrue();
    }

    public void deleteLogicallyByUuid(Double id) {
        orderRepository.deleteLogicallyByUuid(id);
    }
}
