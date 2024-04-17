package com.example.storeapi.service.impl;

import com.example.storeapi.exception.RecordNotFoundException;
import com.example.storeapi.model.ProductResponseModel;
import com.example.storeapi.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    private String productAvailabilityUrl= "http://localhost:9091/fawry/product/{productCode}/availability";
    private String getAllProductsUrl= "http://localhost:9091/fawry/product";
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

    @Override
    public List<ProductResponseModel> getAllProducts() {
        ResponseEntity<List<ProductResponseModel>> response = restTemplate.exchange(
                getAllProductsUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ProductResponseModel>>() {}
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            log.info("All products {}", response.getBody());
            return response.getBody();
        } else {
            throw new RecordNotFoundException("Error retrieving products");
        }
    }

}
