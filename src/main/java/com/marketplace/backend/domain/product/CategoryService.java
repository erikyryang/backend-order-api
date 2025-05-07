package com.marketplace.backend.domain.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;


    public List<CategoryEntity> findAll() {
        return categoryRepository.findAllByActiveTrue();
    }

    public CategoryEntity findByCategoryUuid(UUID categoryUuid) {
        return categoryRepository.findByUuid(categoryUuid).orElseThrow(
                () -> new RuntimeException("Category not found"));
    }

    private Optional<CategoryEntity> findByName(String name) {
        return categoryRepository.findByNameContainingIgnoreCase(name);
    }

    private CategoryEntity save(CategoryEntity category) {
        return categoryRepository.save(category);
    }

    public CategoryEntity saveIfNotExistsByName(String name) {
        Optional<CategoryEntity> categoryOptional = findByName(name);
        if (categoryOptional.isPresent()) {
            return categoryOptional.get();
        } else {
            CategoryEntity category = CategoryEntity.builder().name(name).build();
            return save(category);
        }
    }
}
