package com.marketplace.backend.order.service;

import com.marketplace.backend.order.entity.ProductEntity;
import com.marketplace.backend.order.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    public List<ProductEntity> findAll() {
        return productRepository.findAll();
    }

    public List<ProductEntity> findAllByCategoryUuid(UUID categoryUuid) {
        return productRepository.findAllByCategoryUuidAndActiveTrue(categoryUuid);
    }

    public List<ProductEntity> findByNameContainingIgnoreCase(String trim) {
        return productRepository.findByNameContainingIgnoreCase(trim);
    }

    public ProductEntity save(ProductEntity product) {
        return productRepository.save(product);
    }

    public ProductEntity updateProduct(UUID uuid, ProductEntity updatedProduct) {
        ProductEntity existingProduct = productRepository.findByUuid(uuid);
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
        if (updatedProduct.getCategoryUuid() != null) {
            categoryService.findByCategoryUuid(updatedProduct.getCategoryUuid());
            existingProduct.setCategoryUuid(updatedProduct.getCategoryUuid());
        }
        if (updatedProduct.getImageUrl() != null) {
            existingProduct.setImageUrl(updatedProduct.getImageUrl());
        }
        if (updatedProduct.getQuantity() != null) {
            existingProduct.setQuantity(updatedProduct.getQuantity());
        }
        if (updatedProduct.isActive() != existingProduct.isActive()) {
            existingProduct.setActive(updatedProduct.isActive());
        }

        return productRepository.save(existingProduct);
    }

    public void deleteLogicallyByUuid(UUID uuid) {
        productRepository.deleteLogicallyByUuid(uuid);
    }
}
