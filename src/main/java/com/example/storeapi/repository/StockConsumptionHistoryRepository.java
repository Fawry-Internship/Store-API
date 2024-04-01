package com.example.storeapi.repository;

import com.example.storeapi.entity.StockConsumptionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockConsumptionHistoryRepository extends JpaRepository<StockConsumptionHistory, Long> {

}
