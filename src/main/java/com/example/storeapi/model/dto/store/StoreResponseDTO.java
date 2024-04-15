package com.example.storeapi.model.dto.store;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreResponseDTO {
    private Long id;
    private String name;
    private String address;
}
