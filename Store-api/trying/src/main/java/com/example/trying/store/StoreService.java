package com.example.trying.store;


import com.example.trying.exception.StoreNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StoreService {
    @Autowired
    private StoreRepository storeRepository;

    public Store createStore(Store store) {
        return storeRepository.save(store);
    }

    public List<Store> getAllStores() {
        return storeRepository.findAll();
    }

    public void deleteStoreById(Long storeId) {
        storeRepository.deleteById(storeId);
    }

    public Store updateStore(Long storeId, Store updatedStore) {
        Optional<Store> optionalStore = storeRepository.findById(storeId);

        if (optionalStore.isPresent()) {
            Store existingStore = optionalStore.get();
            existingStore.setName(updatedStore.getName());
            existingStore.setAddress(updatedStore.getAddress());
            return storeRepository.save(existingStore);
        } else {
            throw new StoreNotFoundException("Store with ID " + storeId + " not found.");
        }
    }
}


