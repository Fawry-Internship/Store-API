package com.example.storeapi.service;

import com.example.storeapi.model.ProductResponseModel;

import java.util.List;

public interface ProductService {
    boolean checkProductAvailability(String productCode);
    List<ProductResponseModel> getAllProducts();
}
