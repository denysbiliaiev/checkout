package com.example.checkout.service;

import com.example.checkout.dto.TotalCostDto;
import com.example.checkout.entity.ProductEntity;
import com.example.checkout.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CheckoutService {

    private final ProductRepository productRepository;

    public TotalCostDto getTotalCost(List<Long> productIds) {
        Map<Long, Long> productIdToProductNumberMap = productIds.stream().collect(Collectors.groupingBy(k -> k, Collectors.counting()));
        List<ProductEntity> uniqueProducts = productRepository.findAllById(productIdToProductNumberMap.keySet());
        return calculateTotalCost(uniqueProducts, productIdToProductNumberMap);
    }

    private TotalCostDto calculateTotalCost(List<ProductEntity> uniqueProducts, Map<Long, Long> productIdToProductNumberMap) {
        double total = uniqueProducts.stream().map(product -> {
            long productNumberToBuy = productIdToProductNumberMap.get(product.getId());
            boolean hasSuitableDiscount = product.getDiscount() != null && productNumberToBuy >= product.getDiscount().getCount();

            double totalCostByProduct = productNumberToBuy * product.getPrice();

            if (hasSuitableDiscount) {

                long productNumberRequiredForDiscount = product.getDiscount().getCount();
                long productNumberToBuyWithoutDiscount = productNumberToBuy % productNumberRequiredForDiscount;
                long availableDiscountNumber = (productNumberToBuy - productNumberToBuyWithoutDiscount) / productNumberRequiredForDiscount;
                double totalCostByProductWithoutDiscount = productNumberToBuyWithoutDiscount * product.getPrice();
                double totalCostByProductWithDiscount = availableDiscountNumber * product.getDiscount().getPrice();

                totalCostByProduct = totalCostByProductWithoutDiscount + totalCostByProductWithDiscount;
            }

            return totalCostByProduct;

        }).mapToDouble(m->m).sum();

        TotalCostDto totalCost = new TotalCostDto().setPrice(new DecimalFormat("0.00").format(total));

        return totalCost;
    }
}