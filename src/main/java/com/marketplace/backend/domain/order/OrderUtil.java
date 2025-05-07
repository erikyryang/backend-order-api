package com.marketplace.backend.domain.order;

import com.marketplace.backend.domain.product.ProductEntity;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class OrderUtil {
    public static Double calculateItemsTotal(List<OrderDTO.ItemDTO> itens) {
        return itens.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
    }

    public static List<OrderItemEntity> convertProductsToOrderItem(List<ProductEntity> products, OrderEntity order) {
        if (products == null || products.isEmpty()) {
            return Collections.emptyList();
        }

        return products.stream()
                .filter(Objects::nonNull)
                .map( p -> p.toOrderItemEntity(order))
                .filter(Objects::nonNull)
                .toList();
    }
}
