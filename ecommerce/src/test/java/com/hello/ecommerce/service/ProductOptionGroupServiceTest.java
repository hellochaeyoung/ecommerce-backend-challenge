package com.hello.ecommerce.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hello.ecommerce.dto.OptionDto;
import com.hello.ecommerce.dto.ProductSaveDto;
import com.hello.ecommerce.dto.req.ProductOptionSaveDto;
import com.hello.ecommerce.entity.ProductOptionGroups;
import com.hello.ecommerce.entity.ProductOptions;
import com.hello.ecommerce.repository.ProductOptionGroupsRepository;
import com.hello.ecommerce.repository.ProductOptionsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductOptionGroupServiceTest {

    @Autowired
    ProductOptionGroupsRepository productOptionGroupsRepository;

    @Autowired
    ProductOptionsRepository productOptionsRepository;

    @Test
    void 상품_옵션_추가() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("productOptionSave.json");
        assertNotNull(inputStream, "JSON 파일을 찾을 수 없습니다!");

        OptionDto dto = mapper.readValue(inputStream, OptionDto.class);

        ProductOptionGroups optionGroup = productOptionGroupsRepository.findById(dto.getOptionGroupId())
                .orElseThrow(() -> new RuntimeException("상품 옵션 그룹 정보가 없습니다."));

        ProductOptions entity = dto.toEntity(optionGroup);
        ProductOptions saved = productOptionsRepository.save(entity);

    }

}