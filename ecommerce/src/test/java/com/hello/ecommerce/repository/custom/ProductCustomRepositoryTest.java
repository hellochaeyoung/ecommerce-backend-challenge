package com.hello.ecommerce.repository.custom;

import com.hello.ecommerce.dto.req.ProductListReqDto;
import com.hello.ecommerce.dto.res.ProductResDto;
import com.hello.ecommerce.entity.Products;
import com.hello.ecommerce.repository.ProductsRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class ProductCustomRepositoryTest {

    @Autowired
    ProductsRepository productsRepository;

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

        Pageable pageable = PageRequest.of(dto.getPage() - 1, dto.getPerPage());

        Page<ProductResDto> productsByCondition = productsRepository.findProductsByCondition(dto, pageable);

        assertThat(productsByCondition.getContent().size()).isEqualTo(8);
    }
}