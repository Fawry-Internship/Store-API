package com.example.trying.stock;

import com.example.trying.dto.StockDTO;
import com.example.trying.exception.StockNotFoundException;

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
    public ResponseEntity<StockDTO> searchProduct(@RequestParam Long storeId, @RequestParam Long productCode) {
        try {
            StockDTO stockDTO = stockService.searchProduct(storeId, productCode);
            return ResponseEntity.ok(stockDTO);
        } catch (StockNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/checkAvailability")
    public boolean checkAvailability(@RequestParam Long storeId, @RequestParam Long productCode) {
        return stockService.checkAvailability(storeId, productCode);
    }

    @PostMapping("/add/{storeId}")
    public ResponseEntity<StockDTO> addStock(@PathVariable Long storeId, @RequestBody StockDTO stockDTO) {
        try {
            StockDTO savedStockDTO = stockService.addStock(storeId, stockDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedStockDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/consume")
    public ResponseEntity<String> consumeProduct(@RequestParam Long storeId, @RequestParam Long productCode) {
        return stockService.consumeProduct(storeId, productCode);
    }

    //CRUD
    @DeleteMapping("/deleteAll")
    public void deleteAllStocks() {
        stockService.deleteAllStocks();
    }

    @PutMapping("/update/{stockId}")
    public ResponseEntity<StockDTO> updateStock(@PathVariable Long stockId, @RequestBody StockDTO updatedStockDTO) {
        try {
            StockDTO updatedDTO = stockService.updateStock(stockId, updatedStockDTO);
            return ResponseEntity.ok(updatedDTO);
        } catch (StockNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getAll")
    public List<StockDTO> getAllStocks() {
        return stockService.getAllStocks();
    }
}
