package com.marketplace.backend.order;

import com.marketplace.backend.order.entity.OrderItemEntity;
import com.marketplace.backend.order.entity.ProductEntity;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class OrderUtil {
    public static Double calculateItemsTotal(List<ProductEntity> products) {
        return products.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity()) // TODO: ta com bug
                .sum();
    }

    public static List<OrderItemEntity> convertProductsToOrderItem(List<ProductEntity> products) {
        if (products == null || products.isEmpty()) {
            return Collections.emptyList();
        }

        return products.stream()
                .filter(Objects::nonNull)
                .map(ProductEntity::toOrderItemEntity)
                .filter(Objects::nonNull)
                .toList();
    }
}
