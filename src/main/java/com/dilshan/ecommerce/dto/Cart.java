package com.dilshan.ecommerce.dto;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class Cart {
    private List<CartItem> items = new ArrayList<>();
    private Double total = 0.0;
    private Integer itemCount = 0;
    
    // Calculate totals based on items
    public void recalculate() {
        this.total = items.stream()
            .mapToDouble(CartItem::getSubtotal)
            .sum();
        this.itemCount = items.stream()
            .mapToInt(CartItem::getQuantity)
            .sum();
    }
    
    // Add item to cart
    public void addItem(CartItem newItem) {
        // Check if item already exists
        for (CartItem existing : items) {
            if (existing.getProductId().equals(newItem.getProductId())) {
                existing.setQuantity(existing.getQuantity() + newItem.getQuantity());
                recalculate();
                return;
            }
        }
        items.add(newItem);
        recalculate();
    }
    
    // Update quantity of an item
    public void updateItemQuantity(Long productId, Integer quantity) {
        for (CartItem item : items) {
            if (item.getProductId().equals(productId)) {
                if (quantity <= 0) {
                    items.remove(item);
                } else {
                    item.setQuantity(quantity);
                }
                recalculate();
                return;
            }
        }
    }
    
    // Remove item from cart
    public void removeItem(Long productId) {
        items.removeIf(item -> item.getProductId().equals(productId));
        recalculate();
    }
    
    // Clear all items
    public void clear() {
        items.clear();
        total = 0.0;
        itemCount = 0;
    }
}