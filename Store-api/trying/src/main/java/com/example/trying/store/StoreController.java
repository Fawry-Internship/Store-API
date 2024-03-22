package com.example.trying.store;



import com.example.trying.dto.StoreDTO;
import com.example.trying.mapper.StoreMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/store")
public class StoreController {
    @Autowired
    private StoreService storeService;

    @Autowired
    private StoreMapper storeMapper;

    @DeleteMapping("/delete/{storeId}")
    public void deleteStoreById(@PathVariable Long storeId) {
        storeService.deleteStoreById(storeId);
    }

    @PutMapping("/update/{storeId}")
    public ResponseEntity<StoreDTO> updateStore(@PathVariable Long storeId, @RequestBody StoreDTO updatedStoreDTO) {
        Store updatedStore = storeMapper.storeDTOToStore(updatedStoreDTO);
        Store storedStore = storeService.updateStore(storeId, updatedStore);
        StoreDTO responseStoreDTO = storeMapper.storeToStoreDTO(storedStore);
        return ResponseEntity.ok(responseStoreDTO);
    }

    @PostMapping("/create")
    public ResponseEntity<StoreDTO> createStore(@RequestBody StoreDTO storeDTO) {
        Store store = storeMapper.storeDTOToStore(storeDTO);
        Store createdStore = storeService.createStore(store);
        StoreDTO responseStoreDTO = storeMapper.storeToStoreDTO(createdStore);
        return ResponseEntity.ok(responseStoreDTO);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<StoreDTO>> getAllStores() {
        List<Store> stores = storeService.getAllStores();
        List<StoreDTO> storeDTOs = stores.stream()
                .map(storeMapper::storeToStoreDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(storeDTOs);
    }
}
