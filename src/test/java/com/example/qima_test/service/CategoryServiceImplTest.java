package com.example.qima_test.service;

import com.example.qima_test.domain.model.Category;
import com.example.qima_test.domain.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    void findAll_ShouldReturnAllCategories() {
        // Arrange
        Category category1 = new Category(UUID.randomUUID(), "Electronics", null, Collections.emptyList());
        Category category2 = new Category(UUID.randomUUID(), "Clothing", null, Collections.emptyList());
        when(categoryRepository.findAll()).thenReturn(List.of(category1, category2));

        // Act
        List<Category> result = categoryService.findAll();

        // Assert
        assertEquals(2, result.size());
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void findById_WhenCategoryExists_ShouldReturnCategory() {
        // Arrange
        UUID categoryId = UUID.randomUUID();
        Category expectedCategory = new Category(categoryId, "Electronics", null, Collections.emptyList());
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(expectedCategory));

        // Act
        Category result = categoryService.findById(categoryId);

        // Assert
        assertEquals(expectedCategory, result);
        verify(categoryRepository, times(1)).findById(categoryId);
    }

    @Test
    void findById_WhenCategoryNotExists_ShouldThrowException() {
        // Arrange
        UUID nonExistentId = UUID.randomUUID();
        when(categoryRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> categoryService.findById(nonExistentId));
        verify(categoryRepository, times(1)).findById(nonExistentId);
    }
}