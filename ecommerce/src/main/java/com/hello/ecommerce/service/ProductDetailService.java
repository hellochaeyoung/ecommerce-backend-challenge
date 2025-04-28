package com.hello.ecommerce.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hello.ecommerce.dto.ProductSaveDto;
import com.hello.ecommerce.entity.ProductDetails;
import com.hello.ecommerce.entity.Products;
import com.hello.ecommerce.repository.ProductDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductDetailService {

    private final ProductDetailsRepository productDetailsRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void save(ProductSaveDto dto, Products saved) throws JsonProcessingException {
        String dimension = objectMapper.writeValueAsString(dto.getDetail().getDimensions());
        String additionalInfo = objectMapper.writeValueAsString(dto.getDetail().getAdditionalInfo());
        ProductDetails productDetails = dto.getDetail().toEntity(dimension, additionalInfo, saved);
        productDetailsRepository.save(productDetails);
    }
}
