package com.hello.ecommerce.service;

import com.hello.ecommerce.dto.CategoryDto;
import com.hello.ecommerce.dto.ProductSaveDto;
import com.hello.ecommerce.entity.Categories;
import com.hello.ecommerce.entity.ProductCategories;
import com.hello.ecommerce.entity.Products;
import com.hello.ecommerce.repository.ProductCategoriesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductCategoryService {

    private final ProductCategoriesRepository productCategoriesRepository;
    private final CategoryService categoryService;

    public void saveAll(List<CategoryDto> categoryDtoList, Products saved) {
        List<ProductCategories> productCategories = categoryDtoList.stream()
                .map(cg -> {
                    Categories category = categoryService.selectById(cg.getCategoryId());
                    return ProductCategories.builder()
                            .category(category)
                            .isPrimary(cg.getIsPrimary())
                            .products(saved)
                            .build();
                }).toList();
        productCategoriesRepository.saveAll(productCategories);
    }
}
