package com.example.checkout.controller;

import com.example.checkout.dto.TotalCostDto;
import com.example.checkout.service.CheckoutService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class CheckoutController {

    private final CheckoutService checkoutService;

    @PostMapping("/checkout")
    public ResponseEntity<TotalCostDto> create(@RequestBody List<Long> productIds) {
        return ResponseEntity.ok(checkoutService.getTotalCost(productIds));
    }

}
