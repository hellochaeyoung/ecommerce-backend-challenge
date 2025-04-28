package com.hello.ecommerce.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hello.ecommerce.dto.OptionDto;
import com.hello.ecommerce.dto.ProductSaveDto;
import com.hello.ecommerce.entity.*;
import com.hello.ecommerce.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductsRepository productsRepository;

    private final SellerService sellerService;
    private final BrandService brandService;
    private final ProductDetailService productDetailService;
    private final ProductPriceService productPriceService;
    private final ProductCategoryService productCategoryService;
    private final ProductOptionGroupService productOptionGroupService;
    private final ProductImageService productImageService;
    private final ProductTagService productTagService;

    void save(ProductSaveDto dto) throws JsonProcessingException {
        Sellers seller = sellerService.selectById(dto.getSellerId());
        Brands brand = brandService.selectById(dto.getBrandId());

        Products newProducts = Products.builder()
                .name(dto.getName())
                .slug(dto.getSlug())
                .shortDescription(dto.getShortDescription())
                .fullDescription(dto.getFullDescription())
                .seller(seller)
                .brand(brand)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .status(dto.getStatus())
                .build();

        Products saved = productsRepository.save(newProducts);
        log.info("saved products id: {}", saved.getId());

        productDetailService.save(dto, saved);
        productPriceService.save(dto, saved);
        productCategoryService.saveAll(dto.getCategories(), saved);
        productOptionGroupService.saveAll(dto.getOptionGroups(), saved);
        productImageService.saveAll(dto.getImages(), saved);
        productTagService.saveAll(dto.getTags(), saved);
    }
}
