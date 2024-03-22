package com.example.trying.StockConsumtionHistory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockConsumptionHistoryRepository extends JpaRepository<StockConsumptionHistory, Long> {

}
