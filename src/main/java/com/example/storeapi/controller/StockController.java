package com.example.storeapi.controller;

import com.example.storeapi.entity.StockConsumptionHistory;
import com.example.storeapi.model.ProductResponseModel;
import com.example.storeapi.model.dto.stock.StockRequestDTO;
import com.example.storeapi.model.dto.stock.StockResponseDTO;

import com.example.storeapi.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/stock")
public class StockController {

    private final StockService stockService;

    @DeleteMapping("/delete/{stockId}")
    public ResponseEntity<String> deleteStockById(@PathVariable Long stockId){
        return ResponseEntity.ok(stockService.deleteStockById(stockId));
    }

    @GetMapping("{stockId}")
    public ResponseEntity<StockResponseDTO> findStockById(@PathVariable Long stockId) {
        return ResponseEntity.ok(stockService.findStockById(stockId));
    }

    @GetMapping("/search")
    public ResponseEntity<StockResponseDTO> searchProduct(@RequestParam Long storeId, @RequestParam String productCode) {
        StockResponseDTO stockResponseDTO = stockService.searchProduct(storeId, productCode);
        return ResponseEntity.ok(stockResponseDTO);
    }

    @GetMapping("/checkAvailability")
    public ResponseEntity<Boolean> checkAvailability(@RequestParam Long storeId, @RequestParam String productCode) {
        return ResponseEntity.ok(stockService.checkAvailability(storeId, productCode));
    }

    @PostMapping("/add/{storeId}")
    public ResponseEntity<StockResponseDTO> addStock(@PathVariable Long storeId, @RequestBody StockRequestDTO stockRequestDTO) {
        StockResponseDTO savedStockResponseDTO = stockService.addStock(storeId, stockRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedStockResponseDTO);
    }

    @PostMapping("/consume")
    public ResponseEntity<String> consumeProduct(@RequestParam Long storeId, @RequestParam String productCode) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(stockService.consumeProduct(storeId, productCode));
    }

    @GetMapping("products")
    public ResponseEntity<List<ProductResponseModel>> getAllStocksProducts(){
        return ResponseEntity.ok(stockService.getAllStocksProducts());
    }

    //CRUD
    @DeleteMapping("/deleteAll")
    public void deleteAllStocks() {
        stockService.deleteAllStocks();
    }

    @PutMapping("/update/{stockId}")
    public ResponseEntity<StockResponseDTO> updateStock(@PathVariable Long stockId, @RequestBody StockRequestDTO stockRequestDTO) {
        StockResponseDTO updatedDTO = stockService.updateStock(stockId, stockRequestDTO);
        return ResponseEntity.ok(updatedDTO);
    }

    @GetMapping("/getAll")
    public List<StockResponseDTO> getAllStocks() {
        return stockService.getAllStocks();
    }

    @GetMapping("/consumption-history/{stockId}")
    public ResponseEntity<List<StockConsumptionHistory>> getStockConsumptionHistory(@PathVariable Long stockId){
        return ResponseEntity.ok(stockService.getStockConsumptionHistory(stockId));
    }
}
