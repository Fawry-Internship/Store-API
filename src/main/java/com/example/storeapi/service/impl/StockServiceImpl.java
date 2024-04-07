package com.example.storeapi.service.impl;

import com.example.storeapi.dto.stock.StockRequestDTO;
import com.example.storeapi.dto.stock.StockResponseDTO;
import com.example.storeapi.entity.Stock;
import com.example.storeapi.entity.StockConsumptionHistory;
import com.example.storeapi.entity.Store;
import com.example.storeapi.exception.RecordNotFoundException;
import com.example.storeapi.mapper.StockMapper;
import com.example.storeapi.repository.StockConsumptionHistoryRepository;
import com.example.storeapi.repository.StockRepository;
import com.example.storeapi.repository.StoreRepository;
import com.example.storeapi.service.ProductService;
import com.example.storeapi.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class StockServiceImpl implements StockService {
    private final StockRepository stockRepository;
    private final StoreRepository storeRepository;
    private final StockConsumptionHistoryRepository stockConsumptionHistoryRepository;
    private final StockMapper stockMapper;
    private final ProductService productService;

    public void deleteAllStocks() {
        stockRepository.deleteAll();
    }
    public StockResponseDTO addStock(Long storeId, StockRequestDTO stockRequestDTO) {
        String productCode = stockRequestDTO.getProductCode();
        boolean isProductAvailable = productService.checkProductAvailability(productCode);
        if (!isProductAvailable) {
            log.error("This product with code {} doesn't Exist.", productService);
            throw new RecordNotFoundException("This product " + productCode + " with code doesn't Exist: ");
        }
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("Store with ID " + storeId + " not found."));

        Stock stock = stockMapper.toEntity(stockRequestDTO);
        stock.setStore(store);
        stock.setCreatedAt(LocalDateTime.now());
        stock.setUpdatedAt(LocalDateTime.now());
        Stock savedStock = stockRepository.save(stock);

        // Update stock history
        StockConsumptionHistory history = new StockConsumptionHistory();
        history.setStock(savedStock);
        history.setQuantityBeforeUpdate(savedStock.getQuantity());
        history.setStoreId(storeId);
        history.setCreatedAt(LocalDateTime.now());
        stockConsumptionHistoryRepository.save(history);

        return stockMapper.toDTO(savedStock);
    }

    public List<StockResponseDTO> getAllStocks() {
        List<Stock> stocks = stockRepository.findAll();
        return stocks.stream()
                .map(stockMapper::toDTO)
                .collect(Collectors.toList());
    }

    public StockResponseDTO searchProduct(Long storeId, String productCode) {
        Stock stock = stockRepository.findByStoreIdAndProductCode(storeId, productCode)
                .orElseThrow(() -> new RecordNotFoundException("Stock not found for storeId: " + storeId + " and productCode: " + productCode));

        return stockMapper.toDTO(stock);
    }



    public boolean checkAvailability(Long storeId, String productCode) {
        Optional<Stock> stock = stockRepository.findByStoreIdAndProductCode(storeId, productCode);
        return stock.map(s -> s.getQuantity() > 0).orElse(false);
    }

    @Transactional
    public StockResponseDTO updateStock(Long stockId, StockRequestDTO updatedStockResponseDTO) {
        Stock updatedStock = stockMapper.toEntity(updatedStockResponseDTO);
        Stock existingStock = stockRepository.findById(stockId)
                .orElseThrow(() -> new RecordNotFoundException("Stock with ID " + stockId + " not found."));

        existingStock.setQuantity(updatedStock.getQuantity());
        existingStock.setProductCode(updatedStock.getProductCode());
        return stockMapper.toDTO(stockRepository.save(existingStock));
    }

    @Transactional
    public ResponseEntity<String> consumeProduct(Long storeId, String productCode) {
        boolean isProductAvailable = productService.checkProductAvailability(productCode);
        if (!isProductAvailable) {
            log.error("This product with code {} doesn't Exist.", productService);
            throw new RecordNotFoundException("This product " + productCode + " with code doesn't Exist: ");
        }
        Stock stock = stockRepository.findByStoreIdAndProductCode(storeId, productCode)
                .orElseThrow(() ->{
                    log.error("Stock not found for storeId: " + storeId + ", productCode: " + productCode);
                    return new RecordNotFoundException("Stock not found for storeId: " + storeId + ", productCode: " + productCode);
                });

        if (stock.getQuantity() > 0) {
            int quantityBeforeUpdate = stock.getQuantity();
            stock.setQuantity(quantityBeforeUpdate - 1);
            stockRepository.save(stock);

            // Save consumption history
            StockConsumptionHistory updateHistory = new StockConsumptionHistory();
            updateHistory.setStoreId(storeId);
            updateHistory.setProductCode(productCode);
            updateHistory.setQuantityBeforeUpdate(quantityBeforeUpdate);
            updateHistory.setQuantityAfterUpdate(quantityBeforeUpdate - 1);
            updateHistory.setCreatedAt(LocalDateTime.now());
            updateHistory.setStock(stock);
            stockConsumptionHistoryRepository.save(updateHistory);

            return ResponseEntity.ok("Product consumed successfully");
        } else {
           throw new RecordNotFoundException("Product not available");
        }
    }
}
