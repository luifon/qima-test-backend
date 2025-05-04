package com.example.qima_test.domain.repository;

import com.example.qima_test.domain.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {

    @Query("""
        SELECT p FROM Product p
        LEFT JOIN FETCH p.category
        WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))
          AND (:available IS NULL OR p.available = :available)
          AND (:categoryId IS NULL OR p.category.id = :categoryId)
    """)
    Page<Product> findByFilters(@Param("name") String name,
                                @Param("available") Boolean available,
                                @Param("categoryId") UUID categoryId,
                                Pageable pageable);
}
