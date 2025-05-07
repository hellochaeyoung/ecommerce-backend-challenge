package com.hello.ecommerce.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hello.ecommerce.dto.OptionDto;
import com.hello.ecommerce.entity.ProductOptionGroups;
import com.hello.ecommerce.entity.ProductOptions;
import com.hello.ecommerce.entity.Products;
import com.hello.ecommerce.repository.ProductOptionGroupsRepository;
import com.hello.ecommerce.repository.ProductOptionsRepository;
import com.hello.ecommerce.repository.ProductsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductOptionServiceTest {

    @Autowired
    ProductOptionGroupsRepository productOptionGroupsRepository;

    @Autowired
    ProductOptionsRepository productOptionsRepository;

    @Autowired
    ProductsRepository productsRepository;

    @Test
    void 상품_옵션_추가() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("productOptionSave.json");
        assertNotNull(inputStream, "JSON 파일을 찾을 수 없습니다!");

        OptionDto dto = mapper.readValue(inputStream, OptionDto.class);

        Long productId = 9L;
        Products product = productsRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("상품 정보가 없습니다."));

        ProductOptionGroups optionGroup = productOptionGroupsRepository.findById(dto.getOptionGroupId())
                .orElseThrow(() -> new RuntimeException("상품 옵션 그룹 정보가 없습니다."));

        ProductOptions entity = dto.toEntity(optionGroup);
        ProductOptions saved = productOptionsRepository.save(entity);

    }

    @Test
    @Transactional
    @Rollback(false) // 테스트 코드는 자동 롤백이라 커밋 시점 오지 않기 때문에 추가
    void 상품_옵션_수정() throws IOException {
        Long optionId = 35L;

        ObjectMapper mapper = new ObjectMapper();

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("productOptionUpdate.json");
        assertNotNull(inputStream, "JSON 파일을 찾을 수 없습니다!");

        OptionDto dto = mapper.readValue(inputStream, OptionDto.class);
        dto.setId(optionId);

        ProductOptions productOptions = productOptionsRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("상품 옵션 정보가 없습니다."));

        productOptions.setName(dto.getName());
        productOptions.setAdditionalPrice(dto.getAdditionalPrice());
        productOptions.setSku(dto.getSku());
        productOptions.setStock(dto.getStock());
        productOptions.setDisplayOrder(dto.getDisplayOrder());
    }

}