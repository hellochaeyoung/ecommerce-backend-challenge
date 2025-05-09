package com.hello.ecommerce.dto.req;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ReviewReqDto {

    private Long productId;
    private int page;
    private int perPage;
    private String sort;
    private int rating;
}
