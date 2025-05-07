package com.hello.ecommerce.dto;

import com.hello.ecommerce.entity.ProductOptionGroups;
import com.hello.ecommerce.entity.ProductOptions;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OptionGroupDto {

    private Long id;
    private String name;
    private int displayOrder;
    private List<OptionDto> options;

    public OptionGroupDto(ProductOptionGroups productOptionGroups) {
        this.id = productOptionGroups.getId();
        this.name = productOptionGroups.getName();
        this.displayOrder = productOptionGroups.getDisplayOrder();
        this.options = getOptionDtoList(productOptionGroups.getProductOptions());
    }
    public static List<OptionGroupDto> getOptionGroupDtoList(List<ProductOptionGroups> productOptionGroups) {
        return productOptionGroups.stream().map(OptionGroupDto::new).toList();
    }

    private List<OptionDto> getOptionDtoList(List<ProductOptions> productOptions) {
        return productOptions.stream().map(OptionDto::new).toList();
    }
}
