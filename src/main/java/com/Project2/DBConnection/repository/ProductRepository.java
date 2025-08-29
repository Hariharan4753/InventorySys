package com.Project2.DBConnection.repository; 

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

    List<Product> findByNameContainingIgnoreCase(String keyword);

    List<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);

    List<Product> findByQuantityLessThan(Integer threshold);
}
