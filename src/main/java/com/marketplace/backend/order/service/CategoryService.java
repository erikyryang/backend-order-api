package com.marketplace.backend.order.service;

import com.marketplace.backend.order.entity.CategoryEntity;
import com.marketplace.backend.order.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;


    public List<CategoryEntity> findAll() {
        return categoryRepository.findAll();
    }

    public CategoryEntity findByCategoryUuid(UUID categoryUuid) {
        return categoryRepository.findByUuid(categoryUuid).orElseThrow(
                () -> new RuntimeException("Category not found"));
    }
}
