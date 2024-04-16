package com.example.storeapi.service.impl;

import com.example.storeapi.model.ProductResponseModel;
import com.example.storeapi.model.dto.stock.StockRequestDTO;
import com.example.storeapi.model.dto.stock.StockResponseDTO;
import com.example.storeapi.entity.Stock;
import com.example.storeapi.entity.StockConsumptionHistory;
import com.example.storeapi.entity.Store;
import com.example.storeapi.exception.ConflictException;
import com.example.storeapi.exception.RecordNotFoundException;
import com.example.storeapi.mapper.StockMapper;
import com.example.storeapi.repository.StockConsumptionHistoryRepository;
import com.example.storeapi.repository.StockRepository;
import com.example.storeapi.repository.StoreRepository;
import com.example.storeapi.service.ProductService;
import com.example.storeapi.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        log.info("Admin want to add stock {}, for store with id {}", stockRequestDTO, storeId);

        String productCode = stockRequestDTO.getProductCode();
        stockRepository.findByProductCode(productCode)
                .ifPresent(stock -> {
                    log.error("This stock Already exist for product with code {}", productCode);
                    throw new ConflictException("This stock for product with code " + productCode + " already exists");
                });

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
        log.info("Stock Added successfully {}", stock);

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
        log.info("Admin want to update stock with id {}", stockId);
        Stock updatedStock = stockMapper.toEntity(updatedStockResponseDTO);
        Stock existingStock = stockRepository.findById(stockId)
                .orElseThrow(() -> new RecordNotFoundException("Stock with ID " + stockId + " not found."));

        log.info("stock before update {}", existingStock);

        existingStock.setQuantity(updatedStock.getQuantity());
        existingStock.setProductCode(updatedStock.getProductCode());
        log.info("stock after update {}", existingStock);

        return stockMapper.toDTO(stockRepository.save(existingStock));
    }

    @Transactional
    public String consumeProduct(Long storeId, String productCode) {
        log.info("Customer want to consume this product with code {}, from store with id {}", productCode, storeId);

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

        if (stock.getQuantity() < 1 ) {
            log.error("Product with code {} not available", productCode);
            throw new RecordNotFoundException("Product with code" + productCode + "not available");
        }

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
        log.info("Product consumed successfully");

        return "Product consumed successfully";
    }

    @Override
    public List<ProductResponseModel> getAllStocksProducts() {
        List<ProductResponseModel> allStockProducts = productService.getAllProducts()
                .stream()
                .filter(product -> stockRepository.existsByProductCode(product.getCode()))
                .map(product -> {
                    boolean isActive = checkAvailability(1L, product.getCode());
                    product.setActive(isActive);
                    return product;
                })
                .collect(Collectors.toList());

        log.info("All stocks Products {}", allStockProducts);
        return allStockProducts;
    }

    @Override
    public StockResponseDTO findStockById(Long stockId) {
       Stock stock = stockRepository.findById(stockId)
               .orElseThrow(() -> {
                   log.error("This stock with id {} doesn't exist", stockId);
                  return new RecordNotFoundException("This stock with id " + stockId + " doesn't exist");
               });
       StockResponseDTO stockResponseDTO = stockMapper.toDTO(stock);
       log.info("stock details {}", stockResponseDTO);
       return stockResponseDTO;
    }

    @Override
    public String deleteStockById(Long stockId) {
        Stock stock = stockRepository.findById(stockId)
                .orElseThrow(() -> {
                    log.error("This stock with id {} Already not exist", stockId);
                    return new RecordNotFoundException("This stock with id " + stockId + "Already not exist");
                });
        stockRepository.delete(stock);
        return "success";
    }

}
