package com.example.trying.mapper;



import com.example.trying.dto.StoreDTO;
import com.example.trying.store.Store;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StoreMapper {
    StoreDTO storeToStoreDTO(Store store);
    Store storeDTOToStore(StoreDTO storeDTO);
}
