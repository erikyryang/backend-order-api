package com.marketplace.backend.domain.order;

import com.marketplace.backend.domain.order.dto.OrderDTO;
import com.marketplace.backend.domain.order.dto.UpdateOrderStatusDTO;
import com.marketplace.backend.domain.order.entity.OrderEntity;
import com.marketplace.backend.domain.order.repository.OrderRepository;
import com.marketplace.backend.domain.product.ProductService;
import com.marketplace.backend.domain.product.entity.ProductEntity;
import com.marketplace.backend.domain.order.enums.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.marketplace.backend.util.OrderUtil.calculateItemsTotal;
import static com.marketplace.backend.util.OrderUtil.convertProductsToOrderItem;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final CouponService couponService;

    public OrderEntity create(OrderDTO orderDTO) {
        List<ProductEntity> products = new ArrayList<>();
        orderDTO.getItems().forEach(item -> {
            ProductEntity product = productService.findByUuid(UUID.fromString(item.getUuid()));
            item.setPrice(product.getPrice());
            products.add(product);
        });

        double totalValue = calculateItemsTotal(orderDTO.getItems());
        if(orderDTO.getCoupon() != null && !orderDTO.getCoupon().isBlank()) {
            totalValue = couponService.applyCoupon(orderDTO.getCoupon(), totalValue);
        }

        OrderEntity order = OrderEntity.builder()
                .tableName(orderDTO.getTableName())
                .observations(orderDTO.getObservations())
                .active(true)
                .paymentMethod(orderDTO.getPaymentMethod())
                .status(OrderStatus.PENDING)
                .couponCode(orderDTO.getCoupon())
                .total(totalValue)
                .build();

        order.setItens(convertProductsToOrderItem(products, order));
        return orderRepository.save(order);
    }

    public OrderEntity update(Double id, OrderDTO orderDTO) {
        OrderEntity order = orderRepository.findByActiveTrueAndId(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found: " + id));

        List<ProductEntity> products = new ArrayList<>();

        orderDTO.getItems().forEach(item -> {
            ProductEntity product = productService.findByUuid(UUID.fromString(item.getUuid()));
            item.setPrice(product.getPrice());
            products.add(product);
        });
        order.getItens().clear();
        order.getItens().addAll(convertProductsToOrderItem(products, order));

        double totalValue = calculateItemsTotal(orderDTO.getItems());
        if(orderDTO.getCoupon() != null && !orderDTO.getCoupon().isBlank()) {
            totalValue = couponService.applyCoupon(orderDTO.getCoupon(), totalValue);
        }
        order.setTotal(totalValue);

        if(orderDTO.getPaymentMethod() != null){
            order.setPaymentMethod(orderDTO.getPaymentMethod());
        }

        if(orderDTO.getTableName() != null){
            order.setTableName(orderDTO.getTableName());
        }

        if(orderDTO.getObservations() != null){
            order.setObservations(orderDTO.getObservations());
        }

        return orderRepository.save(order);
    }

    public List<OrderEntity> findById(Double id) {
       if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order UUID cannot be null or empty");
        }

        try {
            OrderEntity order = orderRepository.findByActiveTrueAndId(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found: " + id));
            return List.of(order);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid ID format: " + id);
        }
    }

    public List<OrderEntity> findAll() {
        return orderRepository.findAllByActiveTrue();
    }

    public void deleteLogicallyByUuid(Double id) {
        findById(id);
        orderRepository.deleteLogicallyByUuid(id);
    }

    public OrderEntity updateStatus(Double id, UpdateOrderStatusDTO statusOrderDTO) {
        OrderEntity order = orderRepository.findByActiveTrueAndId(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found: " + id));
        order.setStatus(statusOrderDTO.getStatus());
        return orderRepository.save(order);
    }
}
