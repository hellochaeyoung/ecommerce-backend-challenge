package com.hello.ecommerce.dto.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProductCategoryReqDto {

    private Long categoryId;
    private int page = 0;
    private int perPage = 10;
    private String sort = "created_at:desc";
    private boolean includeSubcategories = true;

}
