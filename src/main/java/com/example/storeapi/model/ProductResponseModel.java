package com.example.storeapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseModel {
    private Long id;
    private String code;
    private String name;
    private String description;
    private Double price;
    private String categoryName;
    private String brand;
    private String imageUrl;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private boolean active;
}
