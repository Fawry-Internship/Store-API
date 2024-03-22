package com.example.trying.stock;

import com.example.trying.StockConsumtionHistory.StockConsumptionHistory;
import com.example.trying.StockConsumtionHistory.StockConsumptionHistoryRepository;
import com.example.trying.dto.StockDTO;
import com.example.trying.exception.StockNotFoundException;
import com.example.trying.mapper.StockMapper;
import com.example.trying.store.Store;
import com.example.trying.store.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StockService {
    private final StockRepository stockRepository;
    private final StoreRepository storeRepository;
    private final StockConsumptionHistoryRepository stockConsumptionHistoryRepository;
    private final StockMapper stockMapper;

    @Autowired
    public StockService(StockRepository stockRepository, StoreRepository storeRepository,
                        StockConsumptionHistoryRepository stockConsumptionHistoryRepository, StockMapper stockMapper) {
        this.stockRepository = stockRepository;
        this.storeRepository = storeRepository;
        this.stockConsumptionHistoryRepository = stockConsumptionHistoryRepository;
        this.stockMapper = stockMapper;
    }
    public void deleteAllStocks() {
        stockRepository.deleteAll();
    }
    public StockDTO addStock(Long storeId, StockDTO stockDTO) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("Store with ID " + storeId + " not found."));

        Stock stock = stockMapper.toEntity(stockDTO);
        stock.setStore(store);
        stock.setCreatedAt(LocalDateTime.now());
        stock.setUpdatedAt(LocalDateTime.now());
        Stock savedStock = stockRepository.save(stock);

        // Update stock history
        StockConsumptionHistory history = new StockConsumptionHistory();
        history.setStock(savedStock);
        history.setQuantityBeforeUpdate(savedStock.getQuantity());
        history.setStoreId(storeId);
        history.setUpdateTime(LocalDateTime.now());
        stockConsumptionHistoryRepository.save(history);

        return stockMapper.toStockDTO(savedStock);
    }

    public List<StockDTO> getAllStocks() {
        List<Stock> stocks = stockRepository.findAll();
        return stocks.stream()
                .map(stockMapper::toStockDTO)
                .collect(Collectors.toList());
    }

    public StockDTO searchProduct(Long storeId, Long productCode) {
        Optional<Stock> stockOptional = stockRepository.findByStoreIdAndProductCode(storeId, productCode);
        if (stockOptional.isPresent()) {
            Stock stock = stockOptional.get();
            return stockMapper.toStockDTO(stock);
        } else {
            throw new StockNotFoundException("Stock not found for storeId: " + storeId + " and productCode: " + productCode);
        }
    }



    public boolean checkAvailability(Long storeId, Long productCode) {
        Optional<Stock> stock = stockRepository.findByStoreIdAndProductCode(storeId, productCode);
        return stock.map(s -> s.getQuantity() > 0).orElse(false);
    }

    @Transactional
    public StockDTO updateStock(Long stockId, StockDTO updatedStockDTO) {
        Stock updatedStock = stockMapper.toEntity(updatedStockDTO);
        Optional<Stock> optionalStock = stockRepository.findById(stockId);

        if (optionalStock.isPresent()) {
            Stock existingStock = optionalStock.get();
            existingStock.setQuantity(updatedStock.getQuantity());
            existingStock.setProductCode(updatedStock.getProductCode());
            existingStock.setStore(updatedStock.getStore());
            return stockMapper.toStockDTO(stockRepository.save(existingStock));
        } else {
            throw new StockNotFoundException("Stock with ID " + stockId + " not found.");
        }
    }

    @Transactional
    public ResponseEntity<String> consumeProduct(Long storeId, Long productCode) {
        Stock stock = stockRepository.findByStoreIdAndProductCode(storeId, productCode)
                .orElseThrow(() -> new StockNotFoundException("Stock not found for storeId: " + storeId + ", productCode: " + productCode));

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
            updateHistory.setUpdateTime(LocalDateTime.now());
            stockConsumptionHistoryRepository.save(updateHistory);

            return ResponseEntity.ok("Product consumed successfully");
        } else {
            return ResponseEntity.badRequest().body("Product not available");
        }
    }


}

