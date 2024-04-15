package com.example.storeapi.mapper;



import com.example.storeapi.model.dto.store.StoreRequestDTO;
import com.example.storeapi.model.dto.store.StoreResponseDTO;
import com.example.storeapi.entity.Store;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StoreMapper {
    StoreResponseDTO toDTO(Store store);
    Store toEntity(StoreRequestDTO storeRequestDTO);
}
