package com.example.trying.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockDTO {
    private Long id;
    private int quantity;
    private Long productCode;
    private Long storeId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}