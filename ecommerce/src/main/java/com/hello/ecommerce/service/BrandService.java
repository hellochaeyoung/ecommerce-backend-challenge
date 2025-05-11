package com.hello.ecommerce.service;

import com.hello.ecommerce.entity.Brands;
import com.hello.ecommerce.repository.BrandsRepository;


public interface BrandService {
    Brands selectById(Long id);
}
