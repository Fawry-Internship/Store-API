package com.example.trying.StockConsumtionHistory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockConsumptionHistoryService {

    private final StockConsumptionHistoryRepository stockHistoryRepository;

    @Autowired
    public StockConsumptionHistoryService(StockConsumptionHistoryRepository historyRepository) {
        this.stockHistoryRepository = historyRepository;
    }

    public List<StockConsumptionHistory> getAllStockUpdateHistory() {
        return stockHistoryRepository.findAll();
    }


}
