package com.hello.ecommerce.dto;

import com.hello.ecommerce.entity.ProductOptions;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OptionDto {

    private String name;
    private int additionalPrice;
    private String sku;
    private int stock;
    private int displayOrder;

    public ProductOptions toEntity() {
        return ProductOptions.builder()
                .name(name)
                .additionalPrice(additionalPrice)
                .sku(sku)
                .stock(stock)
                .displayOrder(displayOrder)
                .build();
    }
}
