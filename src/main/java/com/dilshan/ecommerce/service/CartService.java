package com.dilshan.ecommerce.service;

import com.dilshan.ecommerce.data.Product;
import com.dilshan.ecommerce.dto.Cart;
import com.dilshan.ecommerce.dto.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

@Service
@SessionScope // Cart is stored in user session
public class CartService {
    
    private Cart cart = new Cart();
    
    @Autowired
    private ProductService productService;
    
    public Cart getCart() {
        return cart;
    }
    
    public Cart addToCart(Long productId, Integer quantity) {
        Product product = productService.getProductById(productId);
        
        if (product == null) {
            throw new RuntimeException("Product not found");
        }
        
        if (product.getStock() < quantity) {
            throw new RuntimeException("Insufficient stock");
        }
        
        CartItem item = CartItem.builder()
            .productId(productId)
            .name(product.getName())
            .price(product.getPrice())
            .quantity(quantity)
            .image(product.getImage())
            .build();
        
        cart.addItem(item);
        return cart;
    }
    
    public Cart updateCartItem(Long productId, Integer quantity) {
        cart.updateItemQuantity(productId, quantity);
        return cart;
    }
    
    public Cart removeFromCart(Long productId) {
        cart.removeItem(productId);
        return cart;
    }
    
    public Cart clearCart() {
        cart.clear();
        return cart;
    }
}