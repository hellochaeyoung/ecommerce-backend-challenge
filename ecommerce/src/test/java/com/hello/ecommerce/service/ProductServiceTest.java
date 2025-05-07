package com.hello.ecommerce.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hello.ecommerce.dto.ProductSaveDto;
import com.hello.ecommerce.dto.req.ProductListReqDto;
import com.hello.ecommerce.dto.res.ProductDataResDto;
import com.hello.ecommerce.dto.res.ProductDetailResDto;
import com.hello.ecommerce.entity.*;
import com.hello.ecommerce.repository.BrandsRepository;
import com.hello.ecommerce.repository.CategoriesRepository;
import com.hello.ecommerce.repository.ProductsRepository;
import com.hello.ecommerce.repository.SellersRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private SellersRepository sellersRepository;

    @Autowired
    private BrandsRepository brandsRepository;

    @Autowired
    private CategoriesRepository categoriesRepository;

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

    @Test
    @Transactional
    void 상품_상세_조회() {
        Long id = 1L;
        Products product = productsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("상품 정보가 없습니다."));

        ProductDetailResDto dto = ProductDetailResDto.toDto(product);

        assertThat(dto.getId()).isEqualTo(product.getId());



    }

    @Test
    @Transactional
    void 상품_수정() throws IOException {

        ObjectMapper mapper = new ObjectMapper();

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("productUpdate.json");
        assertNotNull(inputStream, "JSON 파일을 찾을 수 없습니다!");

        ProductSaveDto dto = mapper.readValue(inputStream, ProductSaveDto.class);

        Long id = 1L;
        Products product = productsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("상품 정보가 없습니다."));

        Sellers seller = sellersRepository.findById(dto.getSellerId())
                .orElseThrow(() -> new RuntimeException("셀러 정보가 없습니다."));

        Brands brand = brandsRepository.findById(dto.getBrandId())
                .orElseThrow(() -> new RuntimeException("브랜드 정보가 없습니다"));

        product.setName(dto.getName());
        product.setSlug(dto.getSlug());
        product.setShortDescription(dto.getShortDescription());
        product.setFullDescription(dto.getFullDescription());
        product.setSeller(seller);
        product.setBrand(brand);
        product.setStatus(dto.getStatus());
        product.getDetails().update(dto.getDetail());
        product.getPrices().update(dto.getPrice());


        dto.getCategories().forEach(cg -> {
            Categories category = categoriesRepository.findById(cg.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("카테고리 정보가 없습니다."));

        });

    }

}