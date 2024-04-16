package com.example.storeapi.service;

import com.example.storeapi.model.ProductResponseModel;
import com.example.storeapi.model.dto.stock.StockRequestDTO;
import com.example.storeapi.model.dto.stock.StockResponseDTO;

import java.util.List;


public interface StockService {
    StockResponseDTO addStock(Long storeId, StockRequestDTO stockResponseDTO);

    List<StockResponseDTO> getAllStocks();

    public void deleteAllStocks();

    StockResponseDTO searchProduct(Long storeId, String productCode);

    boolean checkAvailability(Long storeId, String productCode);

    StockResponseDTO updateStock(Long stockId, StockRequestDTO updatedStockResponseDTO);
    String consumeProduct(Long storeId, String productCode);

    List<ProductResponseModel> getAllStocksProducts();

    StockResponseDTO findStockById(Long stockId);

    String deleteStockById(Long stockId);
}

