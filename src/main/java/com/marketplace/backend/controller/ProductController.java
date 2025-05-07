package com.marketplace.backend.controller;

import com.marketplace.backend.domain.product.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductCategoryDTO> findAll() {
        ProductCategoryDTO products = productService.findAll();
        return ResponseEntity.ok(products);
    }

    @GetMapping(value = "/category/{uuid}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProductDTO>> findByCategoryUuid(@PathVariable String uuid) {
        UUID uuidConverted = UUID.fromString(uuid);
        List<ProductDTO> products = productService.findAllByCategoryUuid(uuidConverted);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<ProductEntity>> findByName(@PathVariable String name) {
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
