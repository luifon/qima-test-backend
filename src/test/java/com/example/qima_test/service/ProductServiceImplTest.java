package com.example.qima_test.service;

import com.example.qima_test.domain.model.Category;
import com.example.qima_test.domain.model.Product;
import com.example.qima_test.domain.repository.CategoryRepository;
import com.example.qima_test.domain.repository.ProductRepository;
import com.example.qima_test.dto.ProductByIdResponseDTO;
import com.example.qima_test.dto.ProductRequestDTO;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void findByFilters_ShouldReturnFilteredProducts() {
        // Arrange
        String name = "Laptop";
        Boolean available = true;
        UUID categoryId = UUID.randomUUID();
        Pageable pageable = PageRequest.of(0, 10);

        Product product = createSampleProduct();
        Page<Product> expectedPage = new PageImpl<>(List.of(product));

        when(productRepository.findByFilters(name, available, categoryId, pageable))
                .thenReturn(expectedPage);

        // Act
        Page<Product> result = productService.findByFilters(name, available, categoryId, pageable);

        // Assert
        assertEquals(1, result.getTotalElements());
        verify(productRepository, times(1))
                .findByFilters(name, available, categoryId, pageable);
    }

    @Test
    void save_WithValidData_ShouldReturnSavedProduct() {
        // Arrange
        UUID categoryId = UUID.randomUUID();
        Category category = new Category(categoryId, "Electronics", null, Collections.emptyList());
        ProductRequestDTO request = new ProductRequestDTO(
                "Laptop", "High performance laptop", BigDecimal.valueOf(999.99), true, categoryId);

        Product savedProduct = createSampleProduct();

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        // Act
        ProductByIdResponseDTO result = productService.save(request);

        // Assert
        assertNotNull(result);
        assertEquals("Laptop", result.name());
        verify(categoryRepository, times(1)).findById(categoryId);
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void save_WithInvalidCategory_ShouldThrowException() {
        // Arrange
        UUID nonExistentCategoryId = UUID.randomUUID();
        ProductRequestDTO request = new ProductRequestDTO(
                "Laptop", "Desc", BigDecimal.valueOf(999.99), true, nonExistentCategoryId);

        when(categoryRepository.findById(nonExistentCategoryId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> productService.save(request));
        verify(categoryRepository, times(1)).findById(nonExistentCategoryId);
        verify(productRepository, never()).save(any());
    }

    @Test
    void update_WithValidData_ShouldReturnUpdatedProduct() {
        // Arrange
        UUID productId = UUID.randomUUID();
        UUID categoryId = UUID.randomUUID();

        Category category = new Category(categoryId, "Electronics", null, Collections.emptyList());
        Product existingProduct = createSampleProduct();
        ProductRequestDTO request = new ProductRequestDTO(
                "Updated Laptop", "New description", BigDecimal.valueOf(1099.99), false, categoryId);

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(productRepository.save(any(Product.class))).thenReturn(existingProduct);

        // Act
        ProductByIdResponseDTO result = productService.update(productId, request);

        // Assert
        assertEquals("Updated Laptop", result.name());
        verify(productRepository, times(1)).findById(productId);
        verify(categoryRepository, times(1)).findById(categoryId);
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void delete_ShouldCallRepositoryDelete() {
        // Arrange
        UUID productId = UUID.randomUUID();
        doNothing().when(productRepository).deleteById(productId);

        // Act
        productService.delete(productId);

        // Assert
        verify(productRepository, times(1)).deleteById(productId);
    }

    @Test
    void findById_WhenProductExists_ShouldReturnProduct() {
        // Arrange
        UUID productId = UUID.randomUUID();
        Product product = createSampleProduct();
        product.setId(productId);
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // Act
        ProductByIdResponseDTO result = productService.findById(productId);

        // Assert
        assertNotNull(result);
        assertEquals(productId, result.id());
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void findById_WhenProductNotExists_ShouldThrowException() {
        // Arrange
        UUID nonExistentId = UUID.randomUUID();
        when(productRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> productService.findById(nonExistentId));
        verify(productRepository, times(1)).findById(nonExistentId);
    }

    private Product createSampleProduct() {
        Product product = new Product();
        product.setId(UUID.randomUUID());
        product.setName("Laptop");
        product.setDescription("High performance laptop");
        product.setPrice(BigDecimal.valueOf(999.99));
        product.setAvailable(true);
        product.setCategory(new Category(UUID.randomUUID(), "Electronics", null, Collections.emptyList()));
        return product;
    }
}