package com.marketplace.backend.order.dto;

import com.marketplace.backend.order.enums.OrderStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

@Getter
public class UpdateOrderStatusDTO {
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
