package com.marketplace.backend.domain.order.dto;

import com.marketplace.backend.domain.order.enums.OrderStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

@Getter
public class UpdateOrderStatusDTO {
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
