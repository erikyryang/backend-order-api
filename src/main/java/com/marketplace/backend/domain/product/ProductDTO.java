package com.marketplace.backend.domain.product;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
