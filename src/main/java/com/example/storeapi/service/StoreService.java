package com.example.storeapi.service;


import com.example.storeapi.dto.store.StoreRequestDTO;
import com.example.storeapi.dto.store.StoreResponseDTO;
import com.example.storeapi.entity.Store;

import java.util.List;
import java.util.Optional;

public interface StoreService {
    StoreResponseDTO createStore(StoreRequestDTO store);

    List<StoreResponseDTO> getAllStores();

    String deleteStoreById(Long storeId);

    StoreResponseDTO updateStore(Long storeId, StoreRequestDTO updatedStore);
    Store getById(Long storeId);
}


