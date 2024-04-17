package com.example.storeapi.controller;



import com.example.storeapi.dto.store.StoreRequestDTO;
import com.example.storeapi.dto.store.StoreResponseDTO;
import com.example.storeapi.entity.Store;
import com.example.storeapi.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(path = "/store")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;


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

    @GetMapping("get/{id}")
    public ResponseEntity<Store> getById(@PathVariable Long id) {
        return ResponseEntity.ok(storeService.getById(id));
    }

}
