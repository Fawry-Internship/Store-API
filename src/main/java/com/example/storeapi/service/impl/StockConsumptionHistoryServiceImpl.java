package com.example.storeapi.service.impl;

import com.example.storeapi.entity.StockConsumptionHistory;
import com.example.storeapi.repository.StockConsumptionHistoryRepository;
import com.example.storeapi.service.StockConsumptionHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StockConsumptionHistoryServiceImpl implements StockConsumptionHistoryService {
    private final StockConsumptionHistoryRepository stockHistoryRepository;

    public List<StockConsumptionHistory> getAllStockUpdateHistory() {
        log.info("Admin want to get All stock consumption history");
        List<StockConsumptionHistory> stockConsumptionHistories = stockHistoryRepository.findAll();
        log.info("All stock consumption Histories {}", stockConsumptionHistories);
        return stockConsumptionHistories;
    }

}
