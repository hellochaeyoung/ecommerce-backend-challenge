package com.hello.ecommerce.dto.res;

import com.hello.ecommerce.dto.PaginationDto;
import com.hello.ecommerce.dto.ReviewDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class ReviewResDto {

    private List<ReviewDetailResDto> items;
    private ReviewDto summary;
    private PaginationDto pagination;
}
