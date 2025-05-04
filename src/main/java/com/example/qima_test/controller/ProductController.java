package com.example.qima_test.controller;

import com.example.qima_test.dto.ProductByIdResponseDTO;
import com.example.qima_test.dto.ProductDTO;
import com.example.qima_test.dto.ProductRequestDTO;
import com.example.qima_test.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Page<ProductDTO> findByFilters(@RequestParam(required = false, defaultValue = "") String name,
                                          @RequestParam(required = false) Boolean available,
                                          @RequestParam(required = false) UUID categoryId,
                                          Pageable pageable) {
        return productService.findByFilters(name, available, categoryId, pageable).map(ProductDTO::new);
    }

    @GetMapping("/{id}")
    public ProductByIdResponseDTO findById(@PathVariable UUID id) {
        return productService.findById(id);
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody ProductRequestDTO dto) {
        productService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable UUID id, @RequestBody ProductRequestDTO dto) {
        productService.update(id, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        productService.delete(id);
    }
}
