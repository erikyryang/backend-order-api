package com.marketplace.backend.domain.product.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.marketplace.backend.domain.order.entity.OrderEntity;
import com.marketplace.backend.domain.order.entity.OrderItemEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "product_tbl")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false, unique = true)
    private UUID uuid;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private UUID categoryUuid;

    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String image;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean active = true;

    @Column(nullable = false)
    private String categoryName;

    public OrderItemEntity toOrderItemEntity(OrderEntity order) {
        OrderItemEntity orderItemEntity = new OrderItemEntity();
        orderItemEntity.setName(name);
        orderItemEntity.setActive(true);
        orderItemEntity.setQuantity(quantity);
        orderItemEntity.setPrice(price);
        orderItemEntity.setOrder(order);
        return orderItemEntity;
    }
}
