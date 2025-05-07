package com.hello.ecommerce.dto;

import com.hello.ecommerce.entity.Brands;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BrandDto {

    private Long id;
    private String name;
    private String description;
    private String logoUrl;
    private String website;

    public BrandDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public BrandDto(Brands brand) {
        this.id = brand.getId();
        this.name = brand.getName();
        this.description = brand.getDescription();
        this.logoUrl = brand.getLogoUrl();
        this.website = brand.getWebsite();
    }
}
