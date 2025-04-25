package com.hello.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OptionGroupDto {

    private String name;
    private int displayOrder;
    private List<OptionDto> options;
}
