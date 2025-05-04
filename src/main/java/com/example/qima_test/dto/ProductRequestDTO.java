package com.example.qima_test.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductRequestDTO(
        String name,
        String description,
        BigDecimal price,
        Boolean available,
        UUID categoryId
) {}
