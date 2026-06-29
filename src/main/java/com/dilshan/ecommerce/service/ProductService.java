package com.dilshan.ecommerce.service;

import com.dilshan.ecommerce.data.Product;
import com.dilshan.ecommerce.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;
    
    // Get all products
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    
    // Get product by ID
    public Product getProductById(Long id) {
        return productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }
    
    // Get products by category
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryIgnoreCase(category);
    }
    
    // Search products by name or description
    public List<Product> searchProducts(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return getAllProducts();
        }
        return productRepository.searchProducts(searchTerm.trim());
    }
    
    // Get products by price range
    public List<Product> getProductsByPriceRange(Double minPrice, Double maxPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice);
    }
    
    // Get products by category and price range
    public List<Product> getProductsByCategoryAndPriceRange(
            String category, Double minPrice, Double maxPrice) {
        return productRepository.findByCategoryAndPriceBetween(
            category, minPrice, maxPrice);
    }
    
    // Create new product (Admin only in production)
    public Product createProduct(Product product) {
        product.setCreatedAt(java.time.LocalDateTime.now());
        return productRepository.save(product);
    }
    
    // Update existing product (Admin only in production)
    public Product updateProduct(Long id, Product productDetails) {
        Product existingProduct = getProductById(id);
        
        existingProduct.setName(productDetails.getName());
        existingProduct.setDescription(productDetails.getDescription());
        existingProduct.setPrice(productDetails.getPrice());
        existingProduct.setCategory(productDetails.getCategory());
        existingProduct.setImage(productDetails.getImage());
        existingProduct.setStock(productDetails.getStock());
        existingProduct.setRating(productDetails.getRating());
        existingProduct.setReviews(productDetails.getReviews());
        
        return productRepository.save(existingProduct);
    }
    
    // Update stock quantity
    public Product updateStock(Long id, Integer quantity) {
        Product product = getProductById(id);
        product.setStock(quantity);
        return productRepository.save(product);
    }
    
    // Reduce stock (when order is placed)
    public void reduceStock(Long id, Integer quantity) {
        Product product = getProductById(id);
        if (product.getStock() < quantity) {
            throw new RuntimeException("Insufficient stock for product: " + product.getName());
        }
        product.setStock(product.getStock() - quantity);
        productRepository.save(product);
    }
    
    // Delete product (Admin only in production)
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
    
    // Get top rated products
    public List<Product> getTopRatedProducts(int limit) {
        return productRepository.findTopRatedProducts(limit);
    }
    
    // Get products by multiple categories
    public List<Product> getProductsByCategories(List<String> categories) {
        return productRepository.findByCategoryIn(categories);
    }
}