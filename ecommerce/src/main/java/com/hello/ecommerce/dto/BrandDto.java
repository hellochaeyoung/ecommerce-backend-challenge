package com.hello.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BrandDto {

    private Long id;
    private String name;


}
