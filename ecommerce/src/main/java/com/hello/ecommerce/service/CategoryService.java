package com.hello.ecommerce.service;

import com.hello.ecommerce.dto.CategoryDto;
import com.hello.ecommerce.entity.Categories;
import com.hello.ecommerce.repository.CategoriesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CategoryService {
    Categories selectById(Long id);
    List<CategoryDto> selectAll();
}
