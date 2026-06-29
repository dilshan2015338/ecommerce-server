package com.dilshan.ecommerce.data;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    
    private String fullName;
    private String email;
    private String address;
    private String city;
    private String zipCode;
    private Double total;
    private String status;
    private LocalDateTime orderDate;
    private String confirmationCode;
    
    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderItem> items;
}