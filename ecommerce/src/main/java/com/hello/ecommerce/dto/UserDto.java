package com.hello.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserDto {

    private Long id;
    private String name;
    private String avatarUrl;
}
