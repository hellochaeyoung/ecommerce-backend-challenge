package com.hello.ecommerce.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResDto {

    private String code;
    private String message;
    private DetailsResDto details;

}
