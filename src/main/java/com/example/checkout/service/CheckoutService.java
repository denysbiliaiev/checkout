package com.example.checkout.service;

import com.example.checkout.dto.TotalCostDto;
import com.example.checkout.entity.ProductEntity;
import com.example.checkout.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CheckoutService {

    private final ProductRepository productRepository;

    public TotalCostDto getTotalCost(List<Long> productIds) {
        List<ProductEntity> products = productRepository.findAllById(productIds);
        return new TotalCostDto().setPrice("0.01");
    }
}