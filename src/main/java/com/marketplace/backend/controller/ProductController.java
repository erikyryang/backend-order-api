package com.marketplace.backend.controller;

import com.marketplace.backend.domain.product.ProductService;
import com.marketplace.backend.domain.product.dto.ProductCategoryDTO;
import com.marketplace.backend.domain.product.dto.ProductDTO;
import com.marketplace.backend.domain.product.dto.ProductUpdateDTO;
import com.marketplace.backend.domain.product.entity.ProductEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Tag(name = "Product Management", description = "APIs for managing products")
@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "Retrieve all products", description = "Fetches all products organized by categories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductCategoryDTO.class)))
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductCategoryDTO> findAll() {
        ProductCategoryDTO products = productService.findAll();
        return ResponseEntity.ok(products);
    }

    @Operation(summary = "Retrieve products by category UUID", description = "Fetches all products belonging to a specific category identified by its UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDTO.class))),
            @ApiResponse(responseCode = "404", description = "Category not found", content = @Content)
    })
    @GetMapping(value = "/category/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProductDTO>> findByCategoryUuid(
            @Parameter(description = "UUID of the category", required = true) @PathVariable String uuid) {
        UUID uuidConverted = UUID.fromString(uuid);
        List<ProductDTO> products = productService.findAllByCategoryUuid(uuidConverted);
        return ResponseEntity.ok(products);
    }

    @Operation(summary = "Retrieve products by name", description = "Fetches products matching the provided name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductEntity.class)))
    })
    @GetMapping("/name/{name}")
    public ResponseEntity<List<ProductEntity>> findByName(
            @Parameter(description = "Name of the product", required = true) @PathVariable String name) {
        List<ProductEntity> products = productService.findByName(name);
        return ResponseEntity.ok(products);
    }

    @Operation(summary = "Create a new product", description = "Creates a new product with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductEntity.class))),
            @ApiResponse(responseCode = "400", description = "Invalid product data provided",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Map.class, example = "{\"error\": \"Invalid request\", \"message\": \"Invalid product data\"}")))
    })
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

    @Operation(summary = "Update a product", description = "Updates an existing product identified by its UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductEntity.class))),
            @ApiResponse(responseCode = "400", description = "Invalid product data provided",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Map.class, example = "{\"error\": \"Invalid request\", \"message\": \"Invalid product data\"}"))),
            @ApiResponse(responseCode = "404", description = "Product not found", content = @Content)
    })
    @PutMapping("/{uuid}")
    public ResponseEntity<?> update(
            @Parameter(description = "UUID of the product", required = true) @PathVariable String uuid,
            @RequestBody ProductUpdateDTO product) {
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

    @Operation(summary = "Delete a product", description = "Logically deletes a product identified by its UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product deleted successfully", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid UUID provided",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Map.class, example = "{\"error\": \"Invalid request\", \"message\": \"Invalid UUID format\"}"))),
            @ApiResponse(responseCode = "404", description = "Product not found", content = @Content)
    })
    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> delete(
            @Parameter(description = "UUID of the product", required = true) @PathVariable String uuid) {
        try {
            UUID productUuid = UUID.fromString(uuid);
            productService.deleteLogicallyByUuid(productUuid);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Invalid request");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
}