package com.hello.ecommerce.dto.res;

import com.hello.ecommerce.dto.CategoryDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class ProductMainResDto {
    private List<ProductResDto> newProducts;
    private List<ProductResDto> popularProducts;
    private List<CategoryDto> featuredCategories;
}
