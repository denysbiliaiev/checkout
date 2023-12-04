package com.example.checkout.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TotalCostDto {
    
    private String price;
}
