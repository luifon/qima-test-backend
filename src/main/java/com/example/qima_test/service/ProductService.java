package com.example.qima_test.service;

import com.example.qima_test.domain.model.Product;
import com.example.qima_test.dto.ProductByIdResponseDTO;
import com.example.qima_test.dto.ProductRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ProductService {
    Page<Product> findByFilters(String name, Boolean available, UUID categoryId, Pageable pageable);
    ProductByIdResponseDTO save(ProductRequestDTO product);
    ProductByIdResponseDTO update(UUID id, ProductRequestDTO product);
    void delete(UUID id);
    ProductByIdResponseDTO findById(UUID id);
}
