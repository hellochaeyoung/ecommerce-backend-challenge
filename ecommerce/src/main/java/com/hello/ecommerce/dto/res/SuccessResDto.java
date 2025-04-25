package com.hello.ecommerce.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SuccessResDto {

    private boolean success;
    private DataResDto data;
    private String message;

}
