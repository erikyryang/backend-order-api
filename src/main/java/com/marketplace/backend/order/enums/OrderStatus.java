package com.marketplace.backend.order.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatus {

    PENDING("pending"),
    PREPARING("preparing"),
    READY("ready"),
    DELIVERED("delivered");

    private final String label;

}
