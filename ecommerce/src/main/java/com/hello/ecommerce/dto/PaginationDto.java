package com.hello.ecommerce.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PaginationDto {

    private long totalItems;
    private int totalPages;
    private int currentPage;
    private int perPage;
}
