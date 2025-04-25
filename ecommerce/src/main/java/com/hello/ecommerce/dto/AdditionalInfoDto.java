package com.hello.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AdditionalInfoDto {

    private boolean assemblyRequired;
    private String assemblyTime;
}
