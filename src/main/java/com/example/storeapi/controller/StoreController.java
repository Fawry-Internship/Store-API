package com.example.storeapi.controller;



import com.example.storeapi.model.dto.store.StoreRequestDTO;
import com.example.storeapi.model.dto.store.StoreResponseDTO;
import com.example.storeapi.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(path = "/store")
public class StoreController {
    @Autowired
    private StoreService storeService;


    @DeleteMapping("/delete/{storeId}")
    public ResponseEntity<String> deleteStoreById(@PathVariable Long storeId) {
        return new ResponseEntity<>(storeService.deleteStoreById(storeId), HttpStatus.ACCEPTED);
    }

    @PutMapping("/update/{storeId}")
    public ResponseEntity<StoreResponseDTO> updateStore(@PathVariable Long storeId, @RequestBody StoreRequestDTO storeRequestDTO) {
        return ResponseEntity.ok(storeService.updateStore(storeId, storeRequestDTO));
    }

    @PostMapping("/create")
    public ResponseEntity<StoreResponseDTO> createStore(@RequestBody StoreRequestDTO storeRequestDTO) {
        return ResponseEntity.ok(storeService.createStore(storeRequestDTO));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<StoreResponseDTO>> getAllStores() {
        return ResponseEntity.ok(storeService.getAllStores());
    }
}
