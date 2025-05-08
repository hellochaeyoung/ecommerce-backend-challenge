package com.hello.ecommerce.dto.res;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.hello.ecommerce.dto.CategoryDto;
import com.hello.ecommerce.dto.PaginationDto;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ProductCategoryResDto {

    private CategoryDto category;
    private List<ProductResDto> items;
    private PaginationDto pagination;
}
