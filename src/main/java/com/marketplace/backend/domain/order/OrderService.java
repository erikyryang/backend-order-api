package com.marketplace.backend.domain.order;

import com.marketplace.backend.domain.product.ProductService;
import com.marketplace.backend.domain.product.ProductEntity;
import com.marketplace.backend.domain.order.enums.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.marketplace.backend.domain.order.OrderUtil.calculateItemsTotal;
import static com.marketplace.backend.domain.order.OrderUtil.convertProductsToOrderItem;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;

    public OrderEntity create(CreateOrderDTO createOrderDTO) {
        List<ProductEntity> products = new ArrayList<>();
        createOrderDTO.getItems().forEach(item -> {
            ProductEntity product = productService.findByUuid(UUID.fromString(item.getUuid()));
            item.setPrice(product.getPrice());
            products.add(product);
        });

        OrderEntity order = OrderEntity.builder()
                .tableName(createOrderDTO.getTableName())
                .itens(convertProductsToOrderItem(products))
                .observations(createOrderDTO.getObservations())
                .active(true)
                .paymentMethod(createOrderDTO.getPaymentMethod())
                .status(OrderStatus.PENDING)
                .total(calculateItemsTotal(createOrderDTO.getItems()))
                .build();
        return orderRepository.save(order);
    }

    public OrderEntity update(Double id, UpdateOrderDTO updateOrderDTO) {
        OrderEntity order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        List<ProductEntity> products = new ArrayList<>();

        updateOrderDTO.getItems().forEach(item -> {
            ProductEntity product = productService.findByUuid(UUID.fromString(item.getUuid()));
            item.setPrice(product.getPrice());
            products.add(product);
        });
        order.getItens().clear();
        order.getItens().addAll(convertProductsToOrderItem(products));
        order.setTotal(calculateItemsTotal(updateOrderDTO.getItems()));

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

    public List<OrderEntity> findByOrderId(Double id) {
       if (id == null) {
            throw new IllegalArgumentException("Order UUID cannot be null or empty");
        }

        try {
            OrderEntity order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
            return List.of(order);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid ID format: " + id, e);
        }
    }

    public List<OrderEntity> findAll() {
        return orderRepository.findAllByActiveTrue();
    }

    public void deleteLogicallyByUuid(Double id) {
        orderRepository.deleteLogicallyByUuid(id);
    }

    public OrderEntity updateStatus(Double id, UpdateOrderStatusDTO statusOrderDTO) {
        OrderEntity order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(statusOrderDTO.getStatus());
        return orderRepository.save(order);
    }
}
