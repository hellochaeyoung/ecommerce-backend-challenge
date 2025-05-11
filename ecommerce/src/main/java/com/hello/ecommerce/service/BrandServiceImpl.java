package com.hello.ecommerce.service;

import com.hello.ecommerce.entity.Brands;
import com.hello.ecommerce.repository.BrandsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandsRepository brandsRepository;

    public Brands selectById(Long id) {
        return brandsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("브랜드 정보가 존재하지 않습니다."));
    }

}
