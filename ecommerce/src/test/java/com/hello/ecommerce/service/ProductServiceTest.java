package com.hello.ecommerce.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hello.ecommerce.dto.ProductSaveDto;
import com.hello.ecommerce.dto.req.ProductListReqDto;
import com.hello.ecommerce.dto.res.ProductDataResDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    void 상품_저장() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("productSave.json");
        assertNotNull(inputStream, "JSON 파일을 찾을 수 없습니다!");

        ProductSaveDto productSaveDto = mapper.readValue(inputStream, ProductSaveDto.class);
        productService.save(productSaveDto);

    }

    @Test
    void 상품_목록_조회() {
        ProductListReqDto dto = ProductListReqDto.builder()
                .page(1)
                .perPage(10)
                .sort("created_at:desc")
                .status("ACTIVE")
                .minPrice(10000)
                .maxPrice(1000000)
                .category(new int[]{45})
                .seller(1)
                .brand(1)
                .inStock(true)
                .search("소파")
                .build();

        ProductDataResDto productDataResDto = productService.selectProductList(dto);

        assertThat(productDataResDto).isNotNull();
    }

}