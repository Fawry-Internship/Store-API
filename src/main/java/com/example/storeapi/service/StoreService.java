package com.example.storeapi.service;


<<<<<<< HEAD
import com.example.storeapi.dto.store.StoreRequestDTO;
import com.example.storeapi.dto.store.StoreResponseDTO;
import com.example.storeapi.entity.Store;
=======
import com.example.storeapi.model.dto.store.StoreRequestDTO;
import com.example.storeapi.model.dto.store.StoreResponseDTO;
>>>>>>> 838245fda1c9336b1d9411073d4e4e8badb47ef8

import java.util.List;
import java.util.Optional;

public interface StoreService {
    StoreResponseDTO createStore(StoreRequestDTO store);

    List<StoreResponseDTO> getAllStores();

    String deleteStoreById(Long storeId);

    StoreResponseDTO updateStore(Long storeId, StoreRequestDTO updatedStore);
    Store getById(Long storeId);
}


