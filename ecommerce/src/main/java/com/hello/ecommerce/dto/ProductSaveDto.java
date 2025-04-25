package com.hello.ecommerce.dto;

import com.hello.ecommerce.entity.ProductDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductSaveDto {

    private String name;
    private String slug;
    private String shortDescription;
    private String fullDescription;

    private Long sellerId;
    private Long brandId;
    private String status;

    private DetailDto detail;
    private PriceDto price;

    private List<CategoryDto> categories;
    private List<OptionGroupDto> optionGroups;

    private List<ImageDto> images;
    private List<Long> tags;
}
