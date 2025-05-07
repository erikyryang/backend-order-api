package com.marketplace.backend.domain.product.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private UUID uuid;

    private String name;

    private String description;

    private Double price;

    private UUID categoryUuid;

    private String image;

    private Integer quantity;

    private String categoryName;
}
