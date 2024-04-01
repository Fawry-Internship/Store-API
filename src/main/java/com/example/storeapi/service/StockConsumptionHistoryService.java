package com.example.storeapi.service;

import com.example.storeapi.entity.StockConsumptionHistory;

import java.util.List;

public interface StockConsumptionHistoryService {
    List<StockConsumptionHistory> getAllStockUpdateHistory();
}
