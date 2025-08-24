package com.project2.dbconnection; // NEW: lowercase package name

import io.swagger.v3.oas.annotations.OpenAPIDefinition; // NEW: Swagger/OpenAPI
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Spring Boot Application.
 * Improvements:
 * - Added Swagger/OpenAPI definition for auto-generated API docs
 */
@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Product API",
				version = "1.0",
				description = "REST API for managing products with search, filter, and stock tracking",
				contact = @Contact(name = "Your Name", email = "youremail@example.com")
		)
)
public class DbConnectionApplication {

	public static void main(String[] args) {
		SpringApplication.run(DbConnectionApplication.class, args);
	}
}
