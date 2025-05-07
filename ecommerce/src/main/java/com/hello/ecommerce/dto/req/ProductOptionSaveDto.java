package com.hello.ecommerce.dto.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProductOptionSaveDto {

    private Long optionGroupId;
    private String name;
    private int additionalPrice;
    private String sku;
    private int stock;
    private int displayOrder;
}
