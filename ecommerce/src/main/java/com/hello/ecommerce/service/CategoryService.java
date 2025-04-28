package com.hello.ecommerce.service;

import com.hello.ecommerce.entity.Categories;
import com.hello.ecommerce.repository.CategoriesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoriesRepository categoriesRepository;

    public Categories selectById(Long id) {
        return categoriesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("카테고리 정보가 없습니다."));
    }
}
