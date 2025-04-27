package com.marketplace.backend.order.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private String name;
    private String description;
    private Double price;
    private String categoryName;
    private String imageUrl;
    private Integer quantity;
}
