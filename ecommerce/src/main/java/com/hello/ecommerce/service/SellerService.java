package com.hello.ecommerce.service;

import com.hello.ecommerce.entity.Sellers;
import com.hello.ecommerce.repository.SellersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SellerService {

    private final SellersRepository sellersRepository;

    public Sellers selectById(Long id) {
        return sellersRepository.findById(id).orElseThrow(() -> new RuntimeException("존재하지 않는 셀러입니다."));
    }
}
