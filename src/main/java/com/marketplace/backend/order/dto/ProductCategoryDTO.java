package com.marketplace.backend.order.dto;

import com.marketplace.backend.order.entity.CategoryEntity;
import com.marketplace.backend.order.entity.ProductEntity;
import lombok.Builder;

import java.util.List;

@Builder
public class ProductCategoryDTO {
    private List<CategoryEntity> categories;
    private List<ProductEntity> products;
}
