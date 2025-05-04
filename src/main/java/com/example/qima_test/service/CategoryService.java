package com.example.qima_test.service;

import com.example.qima_test.domain.model.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    List<Category> findAll();
    Category findById(UUID id);
}
