package com.hello.ecommerce.service;

import com.hello.ecommerce.dto.CategoryDto;
import com.hello.ecommerce.entity.Categories;
import com.hello.ecommerce.repository.CategoriesRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class CategoryServiceTest {

    @Autowired
    CategoriesRepository CategoriesRepository;
    @Autowired
    private CategoriesRepository categoriesRepository;

    @Test
    @Transactional
    void 카테고리_전체_조회() {
        List<Categories> byParentIdIsNull = categoriesRepository.findByParentIdIsNull();

        List<CategoryDto> result = byParentIdIsNull.stream()
                .map(this::buildTree)
                .toList();

        assertThat(result.size()).isEqualTo(8);

    }

    CategoryDto buildTree(Categories categories) {
        List<CategoryDto> children = categories.getChildList().stream()
                .map(this::buildTree)
                .toList();

        return new CategoryDto(categories, children);
    }
}