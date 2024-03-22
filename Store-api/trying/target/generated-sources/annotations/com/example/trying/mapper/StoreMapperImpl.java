package com.example.trying.mapper;

import com.example.trying.dto.StoreDTO;
import com.example.trying.store.Store;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-22T07:26:34-0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class StoreMapperImpl implements StoreMapper {

    @Override
    public StoreDTO storeToStoreDTO(Store store) {
        if ( store == null ) {
            return null;
        }

        StoreDTO storeDTO = new StoreDTO();

        storeDTO.setId( store.getId() );
        storeDTO.setName( store.getName() );
        storeDTO.setAddress( store.getAddress() );

        return storeDTO;
    }

    @Override
    public Store storeDTOToStore(StoreDTO storeDTO) {
        if ( storeDTO == null ) {
            return null;
        }

        Store store = new Store();

        store.setId( storeDTO.getId() );
        store.setName( storeDTO.getName() );
        store.setAddress( storeDTO.getAddress() );

        return store;
    }
}
