package com.marketplace.backend.domain.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateOrderDTO {
    private String tableName;
    private String observations;
    private String paymentMethod;
    private List<CreateOrderDTO.ItemDTO> items;

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
