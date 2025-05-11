package com.hello.ecommerce.dto;

import com.hello.ecommerce.entity.ProductOptionGroups;
import com.hello.ecommerce.entity.ProductOptions;
import lombok.*;

import javax.swing.text.html.Option;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OptionDto {

    private Long id;
    private Long optionGroupId;
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

    public ProductOptions toEntity(ProductOptionGroups productOptionGroups) {
        return ProductOptions.builder()
                .name(name)
                .additionalPrice(additionalPrice)
                .sku(sku)
                .stock(stock)
                .displayOrder(displayOrder)
                .productOptionGroup(productOptionGroups)
                .build();
    }

    public OptionDto(ProductOptions option) {
        this.id = option.getId();
        this.optionGroupId = option.getProductOptionGroup().getId();
        this.name = option.getName();
        this.additionalPrice = option.getAdditionalPrice();
        this.sku = option.getSku();
        this.stock = option.getStock();
        this.displayOrder = option.getDisplayOrder();
    }
}
