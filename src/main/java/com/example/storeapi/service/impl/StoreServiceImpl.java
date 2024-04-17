package com.example.storeapi.service.impl;

import com.example.storeapi.model.dto.store.StoreRequestDTO;
import com.example.storeapi.model.dto.store.StoreResponseDTO;
import com.example.storeapi.entity.Store;
import com.example.storeapi.exception.RecordNotFoundException;
import com.example.storeapi.mapper.StoreMapper;
import com.example.storeapi.repository.StoreRepository;
import com.example.storeapi.service.StoreService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;
    private final StoreMapper storeMapper;

    public StoreResponseDTO createStore(StoreRequestDTO storeRequestDTO) {
        log.info("Admin want to Add New store with Details {}", storeRequestDTO);
        Store store = storeMapper.toEntity(storeRequestDTO);
        storeRepository.save(store);
        log.info("Store Added Successfully");
        return storeMapper.toDTO(store);
    }

    public List<StoreResponseDTO> getAllStores() {
        log.info("Admin want to get all stores");
        List<StoreResponseDTO> storeResponseDTOS = storeRepository.findAll()
                .stream()
                .map(storeMapper::toDTO)
                .collect(Collectors.toList());
        log.info("All stores {}", storeResponseDTOS);
        return storeResponseDTOS;
    }

    public String deleteStoreById(Long storeId) {
        log.info("Admin want to delete store with id {}", storeId);
        storeRepository.deleteById(storeId);
        log.info("store deleted successfully");
        return "success";
    }

    public StoreResponseDTO updateStore(Long storeId, StoreRequestDTO storeRequestDTO) {
        log.info("Admin want to update store with id {}", storeId);
        Store existingStore = storeRepository.findById(storeId)
                .orElseThrow(() ->{
                    log.error("this store with id {}, doesn't exist!", storeId);
                    return new RecordNotFoundException("Store with ID " + storeId + " not found.");
                });

        log.info("store before update {}", existingStore);

        existingStore.setName(storeRequestDTO.getName());
        existingStore.setAddress(storeRequestDTO.getAddress());
        storeRepository.save(existingStore);

        log.info("store updated successfully");
        log.info("Store after update {}", existingStore);

        return storeMapper.toDTO(existingStore);
    }

    @Override
    public Store getById(Long storeId) {
        log.info("Fetching store with id: {}", storeId);
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new EntityNotFoundException("Store not found with id: " + storeId));
        log.info("Retrieved store: {}", store);
        return store;
    }

}
