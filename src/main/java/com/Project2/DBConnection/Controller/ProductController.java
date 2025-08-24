package com.Project2.DBConnection.Controller; // NEW: lowercase package name

import com.Project2.DBConnection.Entities.Product;
import com.Project2.DBConnection.Services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * REST API controller for Product.
 * Improvements:
 * - Added new endpoints for search, filter, low-stock, and stock decrease
 * - Proper ResponseEntity usage
 * - Validation with @Valid
 */
@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // Get all products
    @GetMapping
    public ResponseEntity<List<Product>> getAll() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    // Get product by ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    // Create a product
    @PostMapping
    public ResponseEntity<Product> create(@Valid @RequestBody Product product) {
        return new ResponseEntity<>(productService.saveProduct(product), HttpStatus.CREATED);
    }

    // Update a product
    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @Valid @RequestBody Product product) {
        return ResponseEntity.ok(productService.updateProduct(id, product));
    }

    // Delete a product
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    // NEW: Search products by name
    @GetMapping("/search")
    public ResponseEntity<List<Product>> search(@RequestParam String keyword) {
        return ResponseEntity.ok(productService.searchProductsByName(keyword));
    }

    // NEW: Filter products by price range
    @GetMapping("/filter")
    public ResponseEntity<List<Product>> filter(
            @RequestParam BigDecimal minPrice,
            @RequestParam BigDecimal maxPrice) {
        return ResponseEntity.ok(productService.filterProductsByPrice(minPrice, maxPrice));
    }

    // NEW: Get products with low stock
    @GetMapping("/low-stock")
    public ResponseEntity<List<Product>> lowStock(@RequestParam(defaultValue = "5") int threshold) {
        return ResponseEntity.ok(productService.getLowStockProducts(threshold));
    }

    // NEW: Decrease stock for a product
    @PatchMapping("/{id}/decrease-stock")
    public ResponseEntity<Product> decreaseStock(@PathVariable Long id, @RequestParam int count) {
        return ResponseEntity.ok(productService.decreaseProductStock(id, count));
    }
}
