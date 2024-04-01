package com.example.storeapi.service.impl;

import com.example.storeapi.dto.store.StoreRequestDTO;
import com.example.storeapi.dto.store.StoreResponseDTO;
import com.example.storeapi.entity.Store;
import com.example.storeapi.exception.RecordNotFoundException;
import com.example.storeapi.mapper.StoreMapper;
import com.example.storeapi.repository.StoreRepository;
import com.example.storeapi.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;
    private final StoreMapper storeMapper;

    public StoreResponseDTO createStore(StoreRequestDTO storeRequestDTO) {
        Store store = storeMapper.toEntity(storeRequestDTO);
        storeRepository.save(store);
        return storeMapper.toDTO(store);
    }

    public List<StoreResponseDTO> getAllStores() {
        List<StoreResponseDTO> storeResponseDTOS = storeRepository.findAll()
                .stream()
                .map(storeMapper::toDTO)
                .collect(Collectors.toList());
        return storeResponseDTOS;
    }

    public void deleteStoreById(Long storeId) {
        storeRepository.deleteById(storeId);
    }

    public StoreResponseDTO updateStore(Long storeId, StoreRequestDTO storeRequestDTO) {
        Store existingStore = storeRepository.findById(storeId)
                .orElseThrow(() -> new RecordNotFoundException("Store with ID " + storeId + " not found."));

        existingStore.setName(storeRequestDTO.getName());
        existingStore.setAddress(storeRequestDTO.getAddress());
        storeRepository.save(existingStore);
        return storeMapper.toDTO(existingStore);
    }
}
