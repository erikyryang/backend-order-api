package com.marketplace.backend.domain.order.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DiscountEnum {
    PERCENTAGE("percentage"),
    FIXED_VALUE("fixed_value");
    private final String label;
}
