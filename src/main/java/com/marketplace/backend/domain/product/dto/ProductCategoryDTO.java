package com.marketplace.backend.domain.product.dto;

import com.marketplace.backend.domain.product.entity.CategoryEntity;
import com.marketplace.backend.domain.product.entity.ProductEntity;
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
