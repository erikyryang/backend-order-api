package com.marketplace.backend.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateOrderDTO {
    private String tableName;
    private String observations;
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
