package com.hello.ecommerce.dto.req;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ReviewSaveReqDto {

    private Long productId;
    private Long userId;
    private Double rating;
    private String title;
    private String content;
}
