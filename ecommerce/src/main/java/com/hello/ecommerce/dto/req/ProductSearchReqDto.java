package com.hello.ecommerce.dto.req;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductSearchReqDto {

    private String keyword;
    private int page;
    private int perPage;
    private String sort;
    private int[] category;
    private int minPrice;
    private int maxPrice;
    private int[] brand;
    private int[] seller;
    private boolean inStock;
    private double rating;
}
