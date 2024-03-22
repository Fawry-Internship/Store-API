package com.example.trying.mapper;

import com.example.trying.dto.StockDTO;
import com.example.trying.stock.Stock;
import com.example.trying.store.Store;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-22T07:26:34-0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class StockMapperImpl implements StockMapper {

    @Override
    public StockDTO toStockDTO(Stock stock) {
        if ( stock == null ) {
            return null;
        }

        StockDTO stockDTO = new StockDTO();

        stockDTO.setStoreId( stockStoreId( stock ) );
        stockDTO.setId( stock.getId() );
        stockDTO.setQuantity( stock.getQuantity() );
        stockDTO.setProductCode( stock.getProductCode() );
        stockDTO.setCreatedAt( stock.getCreatedAt() );
        stockDTO.setUpdatedAt( stock.getUpdatedAt() );

        return stockDTO;
    }

    @Override
    public Stock toEntity(StockDTO stockDTO) {
        if ( stockDTO == null ) {
            return null;
        }

        Stock stock = new Stock();

        stock.setId( stockDTO.getId() );
        stock.setQuantity( stockDTO.getQuantity() );
        stock.setProductCode( stockDTO.getProductCode() );
        stock.setCreatedAt( stockDTO.getCreatedAt() );
        stock.setUpdatedAt( stockDTO.getUpdatedAt() );

        return stock;
    }

    private Long stockStoreId(Stock stock) {
        if ( stock == null ) {
            return null;
        }
        Store store = stock.getStore();
        if ( store == null ) {
            return null;
        }
        Long id = store.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
