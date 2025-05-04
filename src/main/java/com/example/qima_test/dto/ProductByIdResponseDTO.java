package com.example.qima_test.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductByIdResponseDTO(
        UUID id,
        String name,
        String description,
        BigDecimal price,
        boolean available,
        UUID categoryId
) {}
