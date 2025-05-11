package com.hello.ecommerce.service;

import com.hello.ecommerce.dto.ProductSaveDto;
import com.hello.ecommerce.entity.ProductPrices;
import com.hello.ecommerce.entity.Products;
import com.hello.ecommerce.repository.ProductPricesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductPriceServiceImpl implements ProductPriceService {

    private final ProductPricesRepository productPricesRepository;

    public void save(ProductSaveDto dto, Products saved) {
        ProductPrices productPrice = dto.getPrice().toEntity(saved);
        productPricesRepository.save(productPrice);
    }
}
