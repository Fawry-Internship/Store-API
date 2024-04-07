package com.example.storeapi.dto.stock;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockRequestDTO {
    @Min(value = 0, message = "Quantity must be greater than or equal to zero")
    private int quantity;

    @NotBlank(message = "Product code must not be blank")
    private String productCode;
}
