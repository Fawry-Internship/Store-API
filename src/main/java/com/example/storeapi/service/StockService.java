package com.example.storeapi.service;

import com.example.storeapi.dto.stock.StockRequestDTO;
import com.example.storeapi.dto.stock.StockResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface StockService {
    StockResponseDTO addStock(Long storeId, StockRequestDTO stockResponseDTO);

    List<StockResponseDTO> getAllStocks();

    public void deleteAllStocks();

    StockResponseDTO searchProduct(Long storeId, Long productCode);

    boolean checkAvailability(Long storeId, Long productCode);

    StockResponseDTO updateStock(Long stockId, StockRequestDTO updatedStockResponseDTO);

    ResponseEntity<String> consumeProduct(Long storeId, Long productCode);
}

