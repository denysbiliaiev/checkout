package com.example.checkout.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "product_discount")
public class ProductDiscountEntity {

    @Id
    @GeneratedValue
    private Long id;

    private Long count;

    private double price;
}
