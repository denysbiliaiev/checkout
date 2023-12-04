package com.example.checkout.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "product_discount")
public class ProductDiscountEntity {
    
    @Id
    @GeneratedValue
    private Long id;
    
}
