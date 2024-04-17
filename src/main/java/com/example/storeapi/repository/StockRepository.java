package com.example.storeapi.repository;
import com.example.storeapi.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
    Optional<Stock> findByStoreIdAndProductCode(Long storeId, String productCode);

    Optional<Stock> findByProductCode(String code);

    boolean existsByProductCode(String code);
}
