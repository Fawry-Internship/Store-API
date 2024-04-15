package com.example.storeapi.service;


import com.example.storeapi.model.dto.store.StoreRequestDTO;
import com.example.storeapi.model.dto.store.StoreResponseDTO;

import java.util.List;

public interface StoreService {
    StoreResponseDTO createStore(StoreRequestDTO store);

    List<StoreResponseDTO> getAllStores();

    String deleteStoreById(Long storeId);

    StoreResponseDTO updateStore(Long storeId, StoreRequestDTO updatedStore);
}


