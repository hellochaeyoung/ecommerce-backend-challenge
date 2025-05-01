package com.hello.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ImageDto {

    private String url;
    private String altText;
    private Boolean isPrimary;
    private int displayOrder;
    private Long optionId;

    public ImageDto(String altText, String url) {
        this.altText = altText;
        this.url = url;
    }
}
