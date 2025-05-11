package com.hello.ecommerce.service;

import com.hello.ecommerce.entity.Sellers;
import com.hello.ecommerce.repository.SellersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

public interface SellerService {
    Sellers selectById(Long id);
}
