package com.example.storeapi.mapper;

import com.example.storeapi.dto.stock.StockRequestDTO;
import com.example.storeapi.dto.stock.StockResponseDTO;
import com.example.storeapi.entity.Stock;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StockMapper {

    @Mapping(target = "storeId", source = "store.id")
    StockResponseDTO toDTO(Stock stock);


    Stock toEntity(StockRequestDTO stockResponseDTO);

}
