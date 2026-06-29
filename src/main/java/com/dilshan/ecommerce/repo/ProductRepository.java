package com.dilshan.ecommerce.repo;

import com.dilshan.ecommerce.data.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    // Find products by category (case insensitive)
    List<Product> findByCategoryIgnoreCase(String category);
    
    // Find products by price range
    List<Product> findByPriceBetween(Double minPrice, Double maxPrice);
    
    // Find products by category and price range
    List<Product> findByCategoryAndPriceBetween(
        String category, 
        Double minPrice, 
        Double maxPrice
    );
    
    // Search products by name or description
    @Query("SELECT p FROM Product p WHERE " +
           "LOWER(p.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(p.description) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Product> searchProducts(@Param("searchTerm") String searchTerm);
    
    // Find products with stock greater than zero
    List<Product> findByStockGreaterThan(Integer stock);
    
    // Find products by category and stock
    List<Product> findByCategoryAndStockGreaterThan(String category, Integer stock);
    
    // Get top rated products (limited)
    @Query(value = "SELECT * FROM products ORDER BY rating DESC, reviews DESC LIMIT :limit", 
           nativeQuery = true)
    List<Product> findTopRatedProducts(@Param("limit") int limit);
    
    // Find products by multiple categories
    List<Product> findByCategoryIn(List<String> categories);
    
    // Find products by name containing (case insensitive)
    List<Product> findByNameContainingIgnoreCase(String name);
    
    // Count products by category
    @Query("SELECT p.category, COUNT(p) FROM Product p GROUP BY p.category")
    List<Object[]> countProductsByCategory();
    
    // Get product by stock status
    @Query("SELECT p FROM Product p WHERE " +
           "CASE WHEN p.stock > 0 THEN true ELSE false END = :inStock")
    List<Product> findByStockStatus(@Param("inStock") boolean inStock);
}