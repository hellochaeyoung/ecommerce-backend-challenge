package com.hello.ecommerce.dto;

import com.hello.ecommerce.entity.ProductPrices;
import com.hello.ecommerce.entity.Products;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PriceDto {

    private int basePrice;
    private int salePrice;
    private int costPrice;
    private String currency;
    private int taxRate;
    private int discountPercentage;

    public ProductPrices toEntity(Products products) {
        return ProductPrices.builder()
                .basePrice(basePrice)
                .salePrice(salePrice)
                .costPrice(costPrice)
                .currency(currency)
                .taxRate(taxRate)
                .product(products)
                .build();
    }

    public PriceDto(ProductPrices productPrices) {
        this.basePrice = productPrices.getBasePrice();
        this.salePrice = productPrices.getSalePrice();
        this.currency = productPrices.getCurrency();
        this.taxRate = productPrices.getTaxRate();
        this.discountPercentage
                = getSalePercentage(productPrices.getBasePrice(), productPrices.getSalePrice());
    }

    private int getSalePercentage(int basePrice, int salePrice) {
        return (basePrice - salePrice) / basePrice * 100;
    }
}
