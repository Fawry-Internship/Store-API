package com.example.storeapi.service.impl;

import com.example.storeapi.entity.StockConsumptionHistory;
import com.example.storeapi.repository.StockConsumptionHistoryRepository;
import com.example.storeapi.service.StockConsumptionHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockConsumptionHistoryServiceImpl implements StockConsumptionHistoryService {
    private final StockConsumptionHistoryRepository stockHistoryRepository;

    public List<StockConsumptionHistory> getAllStockUpdateHistory() {
        return stockHistoryRepository.findAll();
    }

}
