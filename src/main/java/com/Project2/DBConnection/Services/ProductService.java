package com.Project2.DBConnection.Services; // NEW: lowercase package name

import com.Project2.DBConnection.Entities.Product;
import com.Project2.DBConnection.repository.ProductRepository;
import com.Project2.DBConnection.Exception.ResourceNotFoundException; // NEW: we'll create this
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * Service layer for Product operations.
 * Improvements:
 * - Added search & filter functions
 * - Centralized exception handling
 * - Transactional for data safety
 * - Stock decrease logic
 */
@Service
@RequiredArgsConstructor // NEW: Lombok generates constructor for final fields
@Transactional // NEW: Ensures DB operations are atomic
public class ProductService {

    private final ProductRepository productRepository;

    // Get all products
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Get product by ID (throws custom exception if not found)
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }

    // Create a new product
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    // Update an existing product
    public Product updateProduct(Long id, Product updatedProduct) {
        Product existing = getProductById(id); // will throw if not found
        existing.setName(updatedProduct.getName());
        existing.setDescription(updatedProduct.getDescription());
        existing.setPrice(updatedProduct.getPrice());
        existing.setQuantity(updatedProduct.getQuantity());
        return productRepository.save(existing);
    }

    // Delete product by ID
    public void deleteProduct(Long id) {
        Product existing = getProductById(id);
        productRepository.delete(existing);
    }

    // NEW: Search products by name
    public List<Product> searchProductsByName(String keyword) {
        return productRepository.findByNameContainingIgnoreCase(keyword);
    }

    // NEW: Filter products by price range
    public List<Product> filterProductsByPrice(BigDecimal minPrice, BigDecimal maxPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice);
    }

    // NEW: Get products with low stock
    public List<Product> getLowStockProducts(int threshold) {
        return productRepository.findByQuantityLessThan(threshold);
    }

    // NEW: Decrease stock for a product
    public Product decreaseProductStock(Long id, int count) {
        Product product = getProductById(id);
        product.decreaseStock(count); // uses entity method to validate
        return productRepository.save(product);
    }
}
