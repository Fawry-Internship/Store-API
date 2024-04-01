package com.example.storeapi.dto.stock;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockResponseDTO {
    private Long id;
    private int quantity;
    private Long productCode;
    private Long storeId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}