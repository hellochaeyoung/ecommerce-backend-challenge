package com.hello.ecommerce.dto;

import com.hello.ecommerce.entity.ProductImages;
import com.hello.ecommerce.entity.ProductOptions;
import com.hello.ecommerce.entity.Products;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ImageDto {

    private Long id;
    private String url;
    private String altText;
    private Boolean isPrimary;
    private int displayOrder;
    private Long optionId;
    private Long productId;

    public ImageDto(String altText, String url) {
        this.altText = altText;
        this.url = url;
    }

    public ImageDto(ProductImages productImages) {
        this.id = productImages.getId();
        this.url = productImages.getUrl();
        this.altText = productImages.getAltText();
        this.isPrimary = productImages.getIsPrimary();
        this.displayOrder = productImages.getDisplayOrder();
        this.optionId = null != productImages.getOption() ? productImages.getOption().getId() : null;
    }

    public ProductImages toEntity(ProductOptions options, Products products) {
        return ProductImages.builder()
                .url(url)
                .altText(altText)
                .isPrimary(isPrimary)
                .displayOrder(displayOrder)
                .option(options)
                .products(products)
                .build();
    }

    public static List<ImageDto> getImageDtoList(List<ProductImages> productImages) {
        return productImages.stream().map(ImageDto::new).toList();
    }
}
