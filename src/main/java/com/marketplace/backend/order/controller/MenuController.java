package com.marketplace.backend.order.controller;

import com.marketplace.backend.order.dto.ProductCategoryDTO;
import com.marketplace.backend.order.entity.ProductEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.marketplace.backend.order.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;


    @GetMapping
    public ResponseEntity<ProductCategoryDTO> getProductsByCategory(
            @RequestParam(defaultValue = "true") boolean retrieveAll,
            @RequestParam(required = false) String categoryUuid) {

        ProductCategoryDTO products = menuService.findProductsByCategory(retrieveAll, categoryUuid);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductEntity>> searchProductsByName(@RequestParam String name) {
        List<ProductEntity> products = menuService.findProductsByName(name);
        return ResponseEntity.ok(products);
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody ProductEntity product) {
        try {
            ProductEntity createdProduct = menuService.createProduct(product);
            return ResponseEntity.status(201).body(createdProduct);
        } catch (IllegalArgumentException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Invalid request");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<?> updateProduct(@PathVariable String uuid, @RequestBody ProductEntity product) {
        try {
            ProductEntity updatedProduct = menuService.updateProduct(uuid, product);
            return ResponseEntity.ok(updatedProduct);
        } catch (IllegalArgumentException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Invalid request");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String uuid) {
        try {
            menuService.deleteProduct(uuid);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Invalid request");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
}
