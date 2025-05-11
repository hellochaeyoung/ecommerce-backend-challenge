package com.hello.ecommerce.dto;

import com.hello.ecommerce.entity.ProductDetails;
import com.hello.ecommerce.entity.Products;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DetailDto {

    private Double weight;
    private DimentionsDto dimensions;
    private String materials;
    private String countryOfOrigin;
    private String warrantyInfo;
    private String careInstructions;
    private Map<String, Object> additionalInfo;

    public ProductDetails toEntity(DimentionsDto dimensions, Map<String, Object> additionalInfo) {
        return ProductDetails.builder()
                .weight(getWeight())
                .dimensions(dimensions)
                .materials(getMaterials())
                .countryOfOrigin(getCountryOfOrigin())
                .warrantyInfo(getWarrantyInfo())
                .careInstructions(getCareInstructions())
                .additionalInfo(additionalInfo)
                .build();
    }

    public DetailDto(ProductDetails detail) {
        this.weight = detail.getWeight();
        this.dimensions = detail.getDimensions();
        this.materials = detail.getMaterials();
        this.countryOfOrigin = detail.getCountryOfOrigin();
        this.warrantyInfo = detail.getWarrantyInfo();
        this.careInstructions = detail.getCareInstructions();
        this.additionalInfo = detail.getAdditionalInfo();
    }
}
