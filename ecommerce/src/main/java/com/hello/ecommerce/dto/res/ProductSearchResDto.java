package com.hello.ecommerce.dto.res;

import com.hello.ecommerce.dto.PaginationDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class ProductSearchResDto {
    private String keyword;
    private Long totalCount;
    private List<ProductResDto> items;
    // filters
    private PaginationDto pagination;
}
