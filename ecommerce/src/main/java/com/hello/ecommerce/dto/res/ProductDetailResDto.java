package com.hello.ecommerce.dto.res;

import com.hello.ecommerce.dto.*;
import com.hello.ecommerce.entity.ProductCategories;
import com.hello.ecommerce.entity.Products;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ProductDetailResDto {

    private Long id;
    private String name;
    private String slug;
    private String shortDescription;
    private String fullDescription;
    private SellerDto seller;
    private BrandDto brand;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private DetailDto detail;
    private PriceDto price;
    private List<CategoryDto> categories;
    private List<OptionGroupDto> optionGroups;
    private List<ImageDto> images;
    private TagDto tags;
    private ReviewDto rating;

    // relatedProducts,,,,,

    public static ProductDetailResDto toDto(Products product) {
        return ProductDetailResDto.builder()
                .id(product.getId())
                .name(product.getName())
                .shortDescription(product.getShortDescription())
                .fullDescription(product.getFullDescription())
                .seller(new SellerDto(product.getSeller()))
                .brand(new BrandDto(product.getBrand()))
                .status(product.getStatus())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .detail(new DetailDto(product.getDetails()))
                .price(new PriceDto(product.getPrices()))
                .categories(CategoryDto.getCategories(product.getCategories()))
                .optionGroups(OptionGroupDto.getOptionGroupDtoList(product.getProductOptionGroups()))
                .images(ImageDto.getImageDtoList(product.getProductImages()))
                .rating(new ReviewDto(product.getReviewsList()))
                .build();
    }


}
