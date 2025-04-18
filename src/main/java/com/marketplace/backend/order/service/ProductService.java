package com.marketplace.backend.order.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marketplace.backend.order.dto.ProductCategoryDTO;
import com.marketplace.backend.order.dto.ProductUpdateDTO;
import com.marketplace.backend.order.entity.CategoryEntity;
import com.marketplace.backend.order.entity.ProductEntity;
import com.marketplace.backend.order.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final ObjectMapper objectMapper;

    public List<ProductEntity> findAll() {
        return productRepository.findAll();
    }

    public ProductEntity findByUuid(UUID uuid) {
        return productRepository.findByUuid(uuid).orElseThrow(() -> new EntityNotFoundException("Product not found"));
    }

    public List<ProductEntity> findAllByCategoryUuid(UUID categoryUuid) {
        return productRepository.findAllByCategoryUuidAndActiveTrue(categoryUuid);
    }

    public List<ProductEntity> findByNameContainingIgnoreCase(String trim) {
        return productRepository.findByNameContainingIgnoreCase(trim);
    }

    public ProductEntity save(ProductUpdateDTO productDTO) {
        ProductEntity product = objectMapper.convertValue(productDTO, ProductEntity.class);

        if (productDTO.getCategoryName() != null) {
            CategoryEntity category = categoryService.saveIfNotExistsByName(productDTO.getCategoryName());
            product.setCategoryUuid(category.getUuid());
        }

        return productRepository.save(product);
    }

    public ProductCategoryDTO findByCategory(boolean retrieveAll, String categoryUuid) {
        if (retrieveAll) {
            List<CategoryEntity> categories = categoryService.findAll();
            List<ProductEntity> products = findAll();
            return ProductCategoryDTO.builder().products(products).categories(categories).build();
        }

        if (categoryUuid == null || categoryUuid.trim().isEmpty()) {
            throw new IllegalArgumentException("Category UUID cannot be null or empty");
        }

        try {
            UUID uuid = UUID.fromString(categoryUuid);
            List<ProductEntity> products = findAllByCategoryUuid(uuid);
            return ProductCategoryDTO.builder().products(products).build();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid UUID format: " + categoryUuid, e);
        }
    }

    public List<ProductEntity> findByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be null or empty");
        }
        return findByNameContainingIgnoreCase(name.trim());
    }

    public ProductEntity update(UUID uuid, ProductUpdateDTO updatedProduct) {
        ProductEntity existingProduct = findByUuid(uuid);
        if (existingProduct == null) {
            throw new IllegalArgumentException("Product not found with UUID: " + uuid);
        }

        if (updatedProduct.getName() != null) {
            existingProduct.setName(updatedProduct.getName());
        }
        if (updatedProduct.getDescription() != null) {
            existingProduct.setDescription(updatedProduct.getDescription());
        }
        if (updatedProduct.getPrice() != null) {
            existingProduct.setPrice(updatedProduct.getPrice());
        }
        if (updatedProduct.getCategoryName() != null) {
            CategoryEntity category = categoryService.saveIfNotExistsByName(updatedProduct.getCategoryName());
            existingProduct.setCategoryUuid(category.getUuid());
        }
        if (updatedProduct.getImageUrl() != null) {
            existingProduct.setImageUrl(updatedProduct.getImageUrl());
        }
        if (updatedProduct.getQuantity() != null) {
            existingProduct.setQuantity(updatedProduct.getQuantity());
        }

        return productRepository.save(existingProduct);
    }

    public void deleteLogicallyByUuid(UUID uuid) {
        productRepository.deleteLogicallyByUuid(uuid);
    }
}
