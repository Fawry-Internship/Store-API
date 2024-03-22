package com.example.trying.mapper;

import com.example.trying.dto.StockDTO;
import com.example.trying.stock.Stock;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StockMapper {

    @Mapping(target = "storeId", source = "store.id")
    StockDTO toStockDTO(Stock stock);


    Stock toEntity(StockDTO stockDTO);

}
