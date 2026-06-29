package com.dilshan.ecommerce.data;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private String category;

    @Column(length = 500)
    private String image;

    @Column(nullable = false)
    private Integer stock = 0;

    private Double rating = 0.0;

    private Integer reviews = 0;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // For sorting/filtering
    private Boolean featured = false;
    private Boolean active = true;
}