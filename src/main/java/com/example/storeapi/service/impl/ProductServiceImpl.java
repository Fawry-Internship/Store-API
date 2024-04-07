package com.example.storeapi.service.impl;

import com.example.storeapi.exception.RecordNotFoundException;
import com.example.storeapi.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    @Value("${product.availability.url}")
    private String productAvailabilityUrl;
    private final RestTemplate restTemplate;

    @Override
    public boolean checkProductAvailability(String productCode) {
        String url = productAvailabilityUrl.replace("{productCode}", String.valueOf(productCode));
        ResponseEntity<Boolean> response = restTemplate.getForEntity(url, Boolean.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new RecordNotFoundException("Error checking product availability");
        }
    }
}
