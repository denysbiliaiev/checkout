package com.example.checkout.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "product")
public class ProductEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private double price;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_discount_id", referencedColumnName = "id")
    private ProductDiscountEntity discount;
}