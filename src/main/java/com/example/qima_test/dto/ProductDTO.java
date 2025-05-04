package com.example.qima_test.dto;

import com.example.qima_test.domain.model.Product;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductDTO(
        UUID id,
        String name,
        String description,
        boolean available,
        BigDecimal price,
        String categoryName
) {
    public ProductDTO(Product product) {
        this(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.isAvailable(),
                product.getPrice(),
                product.getCategory() != null ? product.getCategory().getName() : null
        );
    }
}
