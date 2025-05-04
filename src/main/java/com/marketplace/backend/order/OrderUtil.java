package com.marketplace.backend.order;

import com.marketplace.backend.order.dto.CreateOrderDTO;
import com.marketplace.backend.order.entity.OrderItemEntity;
import com.marketplace.backend.order.entity.ProductEntity;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class OrderUtil {
    public static Double calculateItemsTotal(List<CreateOrderDTO.ItemDTO> itens) {
        return itens.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
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
