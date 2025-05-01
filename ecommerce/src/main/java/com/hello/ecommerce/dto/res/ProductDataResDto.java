package com.hello.ecommerce.dto.res;

import com.hello.ecommerce.dto.PaginationDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDataResDto {

    List<ProductResDto> items;
    PaginationDto pagination;
}
