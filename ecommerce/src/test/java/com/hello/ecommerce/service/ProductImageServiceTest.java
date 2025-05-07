package com.hello.ecommerce.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hello.ecommerce.dto.ImageDto;
import com.hello.ecommerce.dto.OptionDto;
import com.hello.ecommerce.entity.ProductImages;
import com.hello.ecommerce.entity.ProductOptions;
import com.hello.ecommerce.entity.Products;
import com.hello.ecommerce.repository.ProductImagesRepository;
import com.hello.ecommerce.repository.ProductOptionsRepository;
import com.hello.ecommerce.repository.ProductsRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductImageServiceTest {

    @Autowired
    ProductsRepository productsRepository;

    @Autowired
    ProductImagesRepository productImagesRepository;

    @Autowired
    ProductOptionsRepository productOptionsRepository;

    @Test
    void 상품_이미지_추가() throws IOException {
        Long productId = 39L;
        ObjectMapper mapper = new ObjectMapper();

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("productImageSave.json");
        assertNotNull(inputStream, "JSON 파일을 찾을 수 없습니다!");

        ImageDto dto = mapper.readValue(inputStream, ImageDto.class);

        Products products = productsRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("상품 정보가 없습니다."));

        ProductOptions productOptions = productOptionsRepository.findById(dto.getOptionId())
                .orElseThrow(() -> new RuntimeException("상품 옵션 정보가 없습니다."));

        ProductImages saved = productImagesRepository.save(dto.toEntity(productOptions, products));

        assertThat(saved.getUrl()).isEqualTo(dto.getUrl());
        assertThat(saved.getAltText()).isEqualTo(dto.getAltText());
    }

}