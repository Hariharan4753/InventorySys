package com.Project2.DBConnection.Repository; // NEW: lowercase, conventional package name

import com.Project2.DBConnection.Entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Repository interface for Product entity.
 * Improvements:
 * - Package naming convention
 * - Added custom query methods for searching and filtering
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // NEW: Find products containing keyword in name (case-insensitive)
    List<Product> findByNameContainingIgnoreCase(String keyword);

    // NEW: Find products within a price range
    List<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);

    // NEW: Find all products with stock less than a certain amount
    List<Product> findByQuantityLessThan(Integer threshold);
}
