package com.marketplace.backend.domain.product;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductCategoryDTO {
    private List<CategoryEntity> categories;
    private List<ProductEntity> products;
}
