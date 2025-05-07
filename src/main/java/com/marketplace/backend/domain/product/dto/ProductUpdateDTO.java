package com.marketplace.backend.domain.product.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductUpdateDTO {
    private String name;
    private String description;
    private Double price;
    private String categoryName;
    private String image;
    private Integer quantity;
}
