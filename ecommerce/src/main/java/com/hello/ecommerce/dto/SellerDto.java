package com.hello.ecommerce.dto;

import com.hello.ecommerce.entity.Sellers;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class SellerDto {

    private Long id;
    private String name;
    private String description;
    private String logoUrl;
    private Double rating;
    private String contactEmail;
    private String contactPhone;

    public SellerDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public SellerDto(Sellers sellers) {
        this.id = sellers.getId();
        this.name = sellers.getName();
        this.description = sellers.getDescription();
        this.logoUrl = sellers.getLogoUrl();
        this.rating = sellers.getRating();
        this.contactEmail = sellers.getContactEmail();
        this.contactPhone = sellers.getContactPhone();
    }
}
