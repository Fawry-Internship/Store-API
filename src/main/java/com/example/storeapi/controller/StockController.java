package com.example.storeapi.controller;

import com.example.storeapi.dto.stock.StockRequestDTO;
import com.example.storeapi.dto.stock.StockResponseDTO;

import com.example.storeapi.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/stock")
public class StockController {

    private final StockService stockService;

    @Autowired
    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping("/search")
    public ResponseEntity<StockResponseDTO> searchProduct(@RequestParam Long storeId, @RequestParam String productCode) {
        StockResponseDTO stockResponseDTO = stockService.searchProduct(storeId, productCode);
        return ResponseEntity.ok(stockResponseDTO);
    }

    @GetMapping("/checkAvailability")
    public boolean checkAvailability(@RequestParam Long storeId, @RequestParam String productCode) {
        return stockService.checkAvailability(storeId, productCode);
    }

    @PostMapping("/add/{storeId}")
    public ResponseEntity<StockResponseDTO> addStock(@PathVariable Long storeId, @RequestBody StockRequestDTO stockRequestDTO) {
        StockResponseDTO savedStockResponseDTO = stockService.addStock(storeId, stockRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedStockResponseDTO);
    }

    @GetMapping("/consume")
    public ResponseEntity<String> consumeProduct(@RequestParam Long storeId, @RequestParam String productCode) {
        return stockService.consumeProduct(storeId, productCode);
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
}
