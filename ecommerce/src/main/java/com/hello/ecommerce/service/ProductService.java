package com.hello.ecommerce.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hello.ecommerce.dto.PaginationDto;
import com.hello.ecommerce.dto.ProductSaveDto;
import com.hello.ecommerce.dto.req.ProductListReqDto;
import com.hello.ecommerce.dto.req.ProductSearchReqDto;
import com.hello.ecommerce.dto.res.ProductDataResDto;
import com.hello.ecommerce.dto.res.ProductDetailResDto;
import com.hello.ecommerce.dto.res.ProductResDto;
import com.hello.ecommerce.entity.*;
import com.hello.ecommerce.repository.*;
import com.hello.ecommerce.repository.custom.ProductCustomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ProductService {

    private final ProductsRepository productsRepository;
    private final ProductCustomRepository productCustomRepository;

    private final SellerService sellerService;
    private final BrandService brandService;
    private final ProductDetailService productDetailService;
    private final ProductPriceService productPriceService;
    private final ProductCategoryService productCategoryService;
    private final ProductOptionGroupService productOptionGroupService;
    private final ProductImageService productImageService;
    private final ProductTagService productTagService;

    public void save(ProductSaveDto dto) throws JsonProcessingException {
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

    public ProductDataResDto selectProductList(ProductListReqDto dto) {
        Pageable pageable = PageRequest.of(dto.getPage()-1, dto.getPerPage());
        Page<ProductResDto> productList = productCustomRepository.findProductsByCondition(dto, pageable);

        PaginationDto paginationDto = new PaginationDto(productList.getTotalElements(), productList.getTotalPages(), dto.getPage(), dto.getPerPage());

        return new ProductDataResDto(productList.getContent(), paginationDto);
    }

    public ProductDetailResDto selectProductDetail(Long id) {
        Products product = productsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("요청한 상품을 찾을 수 없습니다."));

        return ProductDetailResDto.toDto(product);

    }

    public void updateProduct(Long id, ProductSaveDto dto) {
        Products product = productsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("요청한 상품을 찾을 수 없습니다."));

        Sellers seller = sellerService.selectById(dto.getSellerId());

        Brands brand = brandService.selectById(dto.getBrandId());

        product.setName(dto.getName());
        product.setSlug(dto.getSlug());
        product.setShortDescription(dto.getShortDescription());
        product.setFullDescription(dto.getFullDescription());
        product.setSeller(seller);
        product.setBrand(brand);
        product.setStatus(dto.getStatus());
        product.getDetails().update(dto.getDetail());
        product.getPrices().update(dto.getPrice());

        // 카테고리 등 추가 필요
    }

    public void deleteProduct(Long id) {
        productsRepository.deleteById(id);
    }

    public PageImpl<Products> findProductsBySearch(ProductSearchReqDto dto) {
        return productCustomRepository.findProductsBySearch(dto);
    }

}
