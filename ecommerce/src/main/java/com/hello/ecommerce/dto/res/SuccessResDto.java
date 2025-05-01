package com.hello.ecommerce.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SuccessResDto {

    private boolean success;
    private Object data;
    private String message;

}
