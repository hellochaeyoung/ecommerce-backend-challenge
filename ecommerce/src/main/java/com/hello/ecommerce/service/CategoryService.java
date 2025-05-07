package com.hello.ecommerce.service;

import com.hello.ecommerce.dto.CategoryDto;
import com.hello.ecommerce.entity.Categories;
import com.hello.ecommerce.repository.CategoriesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoriesRepository categoriesRepository;

    public Categories selectById(Long id) {
        return categoriesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("카테고리 정보가 없습니다."));
    }

    public List<CategoryDto> selectAll() {
        List<Categories> byParentIdIsNull = categoriesRepository.findByParentIdIsNull();

        return byParentIdIsNull.stream()
                .map(this::buildTree)
                .toList();
    }

    private CategoryDto buildTree(Categories categories) {
        List<CategoryDto> children = categories.getChildList().stream()
                .map(this::buildTree)
                .toList();

        return new CategoryDto(categories, children);
    }
}
