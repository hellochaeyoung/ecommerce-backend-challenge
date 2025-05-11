package com.hello.ecommerce.service;

import com.hello.ecommerce.dto.ProductSaveDto;
import com.hello.ecommerce.entity.ProductPrices;
import com.hello.ecommerce.entity.Products;
import com.hello.ecommerce.repository.ProductPricesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

public interface ProductPriceService {
    void save(ProductSaveDto dto, Products saved);
}
