package com.hello.ecommerce.dto.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ProductListReqDto {

    private int page = 1;
    private int perPage = 10;
    private String sort;
    private String status;
    private int minPrice;
    private int maxPrice;
    private int[] category;
    private int seller;
    private int brand;
    private boolean inStock;
    private String search;
}
