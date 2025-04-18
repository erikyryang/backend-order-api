package com.marketplace.backend.order.dto;

import com.marketplace.backend.order.entity.CategoryEntity;
import com.marketplace.backend.order.entity.ProductEntity;
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
