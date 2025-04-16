package com.marketplace.backend.order.service;

import com.marketplace.backend.order.dto.ProductCategoryDTO;
import com.marketplace.backend.order.entity.CategoryEntity;
import com.marketplace.backend.order.entity.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final CategoryService categoryService;
    private final ProductService productService;

    public ProductCategoryDTO findProductsByCategory(boolean retrieveAll, String categoryUuid) {
        if (retrieveAll) {
            List<CategoryEntity> categories = categoryService.findAll();
            List<ProductEntity> products = productService.findAll();
            return ProductCategoryDTO.builder().products(products).categories(categories).build();
        }

        if (categoryUuid == null || categoryUuid.trim().isEmpty()) {
            throw new IllegalArgumentException("Category UUID cannot be null or empty");
        }

        try {
            UUID uuid = UUID.fromString(categoryUuid);
            List<ProductEntity> products = productService.findAllByCategoryUuid(uuid);
            return ProductCategoryDTO.builder().products(products).build();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid UUID format: " + categoryUuid, e);
        }
    }

    public List<ProductEntity> findProductsByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be null or empty");
        }
        return productService.findByNameContainingIgnoreCase(name.trim());
    }

    public ProductEntity createProduct(ProductEntity product) {
        return productService.save(product);
    }

    public void deleteProduct(String uuid) {
        UUID productUuid = UUID.fromString(uuid);
        productService.deleteLogicallyByUuid(productUuid);
    }

    public ProductEntity updateProduct(String productUuid, ProductEntity product) {
        UUID uuid = UUID.fromString(productUuid);
        return productService.updateProduct(uuid, product);
    }
}
