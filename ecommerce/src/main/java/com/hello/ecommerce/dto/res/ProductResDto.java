package com.hello.ecommerce.dto.res;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hello.ecommerce.dto.BrandDto;
import com.hello.ecommerce.dto.ImageDto;
import com.hello.ecommerce.dto.SellerDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductResDto {

    private Long id;
    private String name;
    private String slug;
    private String shortDescription;
    private int basePrice;
    private int salePrice;
    private String currency;
    private ImageDto primaryImage;
    private BrandDto brand;
    private SellerDto seller;
    private Double rating;
    private int reviewCount;
    private boolean inStock;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ProductResDto(Long id, String name, String slug, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.updatedAt = updatedAt;
    }

    public ProductResDto(Long id, String name, String slug, String shortDescription, int basePrice, int salePrice, String currency, ImageDto primaryImage, BrandDto brand, SellerDto seller, double rating, int reviewCount, boolean inStock, String status, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.shortDescription = shortDescription;
        this.basePrice = basePrice;
        this.salePrice = salePrice;
        this.currency = currency;
        this.primaryImage = primaryImage;
        this.brand = brand;
        this.seller = seller;
        this.rating = rating;
        this.reviewCount = reviewCount;
        this.inStock = inStock;
        this.status = status;
        this.createdAt = createdAt;
    }
}
