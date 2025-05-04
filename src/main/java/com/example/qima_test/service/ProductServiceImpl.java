package com.example.qima_test.service;

import com.example.qima_test.domain.model.Category;
import com.example.qima_test.domain.model.Product;
import com.example.qima_test.domain.repository.CategoryRepository;
import com.example.qima_test.domain.repository.ProductRepository;
import com.example.qima_test.dto.ProductByIdResponseDTO;
import com.example.qima_test.dto.ProductRequestDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Page<Product> findByFilters(String name, Boolean available, UUID categoryId, Pageable pageable) {
        return productRepository.findByFilters(name, available, categoryId, pageable);
    }

    @Override
    public ProductByIdResponseDTO save(ProductRequestDTO dto) {
        Category category = categoryRepository.findById(dto.categoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        Product product = new Product();
        product.setName(dto.name());
        product.setDescription(dto.description());
        product.setPrice(dto.price());
        product.setAvailable(dto.available());
        product.setCategory(category);

        Product save = productRepository.save(product);
        return toDTO(save);
    }

    public ProductByIdResponseDTO update(UUID id, ProductRequestDTO dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        Category category = categoryRepository.findById(dto.categoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        product.setName(dto.name());
        product.setDescription(dto.description());
        product.setPrice(dto.price());
        product.setAvailable(dto.available());
        product.setCategory(category);

        Product save = productRepository.save(product);
        return toDTO(save);
    }

    @Override
    public void delete(UUID id) {
        productRepository.deleteById(id);
    }

    @Override
    public ProductByIdResponseDTO findById(UUID id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found."));
        return toDTO(product);

    }

    private static ProductByIdResponseDTO toDTO(Product product) {
        return new ProductByIdResponseDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.isAvailable(),
                product.getCategory() != null ? product.getCategory().getId() : null
        );
    }
}
