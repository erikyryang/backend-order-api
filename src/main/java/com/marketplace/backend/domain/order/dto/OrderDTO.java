package com.marketplace.backend.domain.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderDTO {
    private String tableName;
    private String observations;
    private String paymentMethod;
    private String coupon;
    private List<ItemDTO> items;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemDTO {
        private String uuid;
        private String name;
        private Integer quantity;
        private Double price;
    }
}
