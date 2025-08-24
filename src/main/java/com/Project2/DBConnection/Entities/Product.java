package com.Project2.DBConnection.Entities; // NEW: lowercase, conventional package name

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;                       // NEW: Lombok for boilerplate
import org.hibernate.annotations.CreationTimestamp; // NEW: audit timestamps
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;          // NEW: use BigDecimal for currency-safe price
import java.time.Instant;

/**
 * Product entity representing items in the catalog.
 * Improvements:
 * - Package & naming conventions
 * - Validation annotations
 * - Extra fields: description, quantity, createdAt, updatedAt
 * - Index on name for faster searches
 * - Lombok to remove boilerplate
 */
@Entity
@Table(
        name = "products",
        indexes = {
                @Index(name = "idx_products_name", columnList = "name") // NEW: speeds up name search
        }
)
@Getter @Setter               // NEW: Lombok getters/setters
@NoArgsConstructor            // NEW
@AllArgsConstructor           // NEW
@Builder                      // NEW: convenient builder for tests/seeding
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name cannot be empty")          // NEW: validation
    @Size(max = 100, message = "Name must be at most 100 characters")
    @Column(nullable = false, length = 100)
    private String name;

    @Size(max = 300, message = "Description must be at most 300 characters") // NEW
    @Column(length = 300)
    private String description; // NEW: optional short description

    @NotNull(message = "Price is required")              // NEW
    @Positive(message = "Price must be positive")
    @Column(nullable = false, precision = 10, scale = 2) // NEW: currency-safe column
    private BigDecimal price; // NEW: use BigDecimal instead of double

    @NotNull
    @PositiveOrZero
    @Column(nullable = false)
    private Integer quantity; // NEW: basic stock tracking

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt; // NEW: audit

    @UpdateTimestamp
    private Instant updatedAt; // NEW: audit

    // NEW: convenience method that enforces non-negative stock
    public void decreaseStock(int count) {
        if (count < 0) throw new IllegalArgumentException("count cannot be negative");
        int newQty = this.quantity - count;
        if (newQty < 0) throw new IllegalStateException("Insufficient stock");
        this.quantity = newQty;
    }
}
