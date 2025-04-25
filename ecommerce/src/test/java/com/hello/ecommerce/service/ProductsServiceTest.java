package com.hello.ecommerce.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hello.ecommerce.dto.ProductSaveDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductsServiceTest {

    @Autowired
    private ProductsService productsService;

    @Test
    void 상품_저장() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("productSave.json");
        assertNotNull(inputStream, "JSON 파일을 찾을 수 없습니다!");

        ProductSaveDto productSaveDto = mapper.readValue(inputStream, ProductSaveDto.class);
        productsService.save(productSaveDto);

    }

}