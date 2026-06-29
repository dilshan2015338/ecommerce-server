package com.dilshan.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    private Long productId;
    private String name;
    private Double price;
    private Integer quantity;
    private String image;
    private Double subtotal; // Calculated field

    // Calculate subtotal
    public Double getSubtotal() {
        return price * quantity;
    }
}