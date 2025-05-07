package com.marketplace.backend.domain.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marketplace.backend.domain.product.dto.ProductCategoryDTO;
import com.marketplace.backend.domain.product.dto.ProductDTO;
import com.marketplace.backend.domain.product.dto.ProductUpdateDTO;
import com.marketplace.backend.domain.product.entity.CategoryEntity;
import com.marketplace.backend.domain.product.entity.ProductEntity;
import com.marketplace.backend.domain.product.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final ObjectMapper objectMapper;

    public List<ProductEntity> findAllByActiveTrue() {
        return productRepository.findAllByActiveTrue();
    }

    public ProductEntity findByUuid(UUID uuid) {
        return productRepository.findByUuid(uuid).orElseThrow(() -> new EntityNotFoundException("Product not found"));
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

    public ProductCategoryDTO findAll() {
        List<CategoryEntity> categories = categoryService.findAll();
        List<ProductEntity> products = findAllByActiveTrue();
        return ProductCategoryDTO.builder().products(products).categories(categories).build();
    }

    public List<ProductDTO> findAllByCategoryUuid(UUID categoryUuid) {
        List<ProductEntity> products = productRepository.findAllByCategoryUuidAndActiveTrue(categoryUuid);
        return products.stream().map(p -> objectMapper.convertValue(p, ProductDTO.class)).toList();
    }

    public List<ProductEntity> findByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product name cannot be null or empty");
        }
        return findByNameContainingIgnoreCase(name.trim());
    }

    public ProductEntity update(UUID uuid, ProductUpdateDTO updatedProduct) {
        ProductEntity existingProduct = findByUuid(uuid);
        if (existingProduct == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ("Product not found with UUID: " + uuid));
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
            existingProduct.setCategoryName(category.getName());
        }
        if (updatedProduct.getImage() != null) {
            existingProduct.setImage(updatedProduct.getImage());
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
