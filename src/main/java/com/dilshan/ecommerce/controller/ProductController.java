package com.dilshan.ecommerce.controller;

import com.dilshan.ecommerce.data.Product;
import com.dilshan.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {

    @Autowired
    private ProductService productService;

    // 1. Get all products
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    // 2. Get product by ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        try {
            Product product = productService.getProductById(id);
            return ResponseEntity.ok(product);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 3. Get products by category
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable String category) {
        List<Product> products = productService.getProductsByCategory(category);
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(products);
    }

    // 4. Search products
    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam(required = false) String q) {
        List<Product> products = productService.searchProducts(q);
        return ResponseEntity.ok(products);
    }

    // 5. Get products by price range
    @GetMapping("/price-range")
    public ResponseEntity<List<Product>> getProductsByPriceRange(
            @RequestParam Double min,
            @RequestParam Double max) {
        List<Product> products = productService.getProductsByPriceRange(min, max);
        return ResponseEntity.ok(products);
    }

    // 6. Get products by category and price range
    @GetMapping("/filter")
    public ResponseEntity<List<Product>> filterProducts(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice) {

        List<Product> products;
        if (category != null && minPrice != null && maxPrice != null) {
            products = productService.getProductsByCategoryAndPriceRange(
                    category, minPrice, maxPrice);
        } else if (category != null) {
            products = productService.getProductsByCategory(category);
        } else if (minPrice != null && maxPrice != null) {
            products = productService.getProductsByPriceRange(minPrice, maxPrice);
        } else {
            products = productService.getAllProducts();
        }
        return ResponseEntity.ok(products);
    }

    // 7. Get top rated products
    @GetMapping("/top-rated")
    public ResponseEntity<List<Product>> getTopRatedProducts(
            @RequestParam(defaultValue = "5") int limit) {
        List<Product> products = productService.getTopRatedProducts(limit);
        return ResponseEntity.ok(products);
    }

    // 8. Get products by categories (multiple)
    @GetMapping("/categories")
    public ResponseEntity<List<Product>> getProductsByCategories(
            @RequestParam List<String> categories) {
        List<Product> products = productService.getProductsByCategories(categories);
        return ResponseEntity.ok(products);
    }

    // 9. Get products by stock status
    @GetMapping("/in-stock")
    public ResponseEntity<List<Product>> getProductsInStock(
            @RequestParam(defaultValue = "true") boolean inStock) {
        // Note: This uses custom query in repository
        return ResponseEntity.ok(null); // Implement as needed
    }

    // 10. Create new product (Admin only - for demo)
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product createdProduct = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    // 11. Update product (Admin only - for demo)
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long id,
            @RequestBody Product product) {
        try {
            Product updatedProduct = productService.updateProduct(id, product);
            return ResponseEntity.ok(updatedProduct);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 12. Update stock (Admin only - for demo)
    @PatchMapping("/{id}/stock")
    public ResponseEntity<Product> updateStock(
            @PathVariable Long id,
            @RequestParam Integer quantity) {
        try {
            Product updatedProduct = productService.updateStock(id, quantity);
            return ResponseEntity.ok(updatedProduct);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 13. Delete product (Admin only - for demo)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}