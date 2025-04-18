package com.marketplace.backend.order.controller;

import com.marketplace.backend.order.dto.ProductCategoryDTO;
import com.marketplace.backend.order.dto.ProductUpdateDTO;
import com.marketplace.backend.order.entity.ProductEntity;
import com.marketplace.backend.order.service.ProductService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductCategoryDTO> getByCategory(
            @RequestParam(defaultValue = "true") boolean retrieveAll,
            @RequestParam(required = false) String categoryUuid) {

        ProductCategoryDTO products = productService.findByCategory(retrieveAll, categoryUuid);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductEntity>> searchByName(@RequestParam String name) {
        List<ProductEntity> products = productService.findByName(name);
        return ResponseEntity.ok(products);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ProductUpdateDTO product) {
        try {
            ProductEntity createdProduct = productService.save(product);
            return ResponseEntity.status(201).body(createdProduct);
        } catch (IllegalArgumentException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Invalid request");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<?> update(@PathVariable String uuid, @RequestBody ProductUpdateDTO product) {
        try {
            UUID productUuid = UUID.fromString(uuid);
            ProductEntity updatedProduct = productService.update(productUuid, product);
            return ResponseEntity.ok(updatedProduct);
        } catch (IllegalArgumentException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Invalid request");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> delete(@PathVariable String uuid) {
        try {
            UUID productUuid = UUID.fromString(uuid);
            productService.deleteLogicallyByUuid(productUuid);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Invalid request");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
}
