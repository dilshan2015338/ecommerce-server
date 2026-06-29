package com.dilshan.ecommerce.controller;


import com.dilshan.ecommerce.dto.Cart;
import com.dilshan.ecommerce.dto.CartRequest;
import com.dilshan.ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "http://localhost:3000")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping
    public ResponseEntity<Cart> getCart() {
        return ResponseEntity.ok(cartService.getCart());
    }

    @PostMapping("/items")
    public ResponseEntity<Cart> addToCart(@RequestBody CartRequest request) {
        try {
            Cart updatedCart = cartService.addToCart(
                    request.getProductId(),
                    request.getQuantity()
            );
            return ResponseEntity.ok(updatedCart);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/items/{productId}")
    public ResponseEntity<Cart> updateCartItem(
            @PathVariable Long productId,
            @RequestBody CartRequest request) {
        Cart updatedCart = cartService.updateCartItem(productId, request.getQuantity());
        return ResponseEntity.ok(updatedCart);
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<Cart> removeFromCart(@PathVariable Long productId) {
        Cart updatedCart = cartService.removeFromCart(productId);
        return ResponseEntity.ok(updatedCart);
    }

    @DeleteMapping("/items/productId")
    public ResponseEntity<Cart> clearCart(@PathVariable Long productId) {
        Cart emptyCart = cartService.clearCart();
        return ResponseEntity.ok(emptyCart);
    }
}