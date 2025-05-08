package com.hello.ecommerce.service;

import com.hello.ecommerce.dto.BrandDto;
import com.hello.ecommerce.dto.CategoryDto;
import com.hello.ecommerce.dto.ImageDto;
import com.hello.ecommerce.dto.PaginationDto;
import com.hello.ecommerce.dto.res.ProductCategoryResDto;
import com.hello.ecommerce.dto.res.ProductResDto;
import com.hello.ecommerce.entity.Categories;
import com.hello.ecommerce.entity.ProductCategories;
import com.hello.ecommerce.entity.ProductImages;
import com.hello.ecommerce.entity.Products;
import com.hello.ecommerce.repository.CategoriesRepository;
import com.hello.ecommerce.repository.ProductCategoriesRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductCategoryServiceTest {

    @Autowired
    private ProductCategoriesRepository productCategoriesRepository;

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Test
    @Transactional
    void 카테고리_상품_조회() {

        Long categoryId = 17L;
        int page = 0;
        int perPage = 10;
        String sort = "createdAt:desc";
        boolean includeSubcategories = true;

        Sort sortCondition = parseSort(sort);
        Pageable pageable = PageRequest.of(page, perPage);
        Page<ProductCategories> result = productCategoriesRepository.findByCategoryId(categoryId, pageable);

        List<Products> productsList = result.getContent().stream().map(ProductCategories::getProducts).toList();
        productsList = productsList.stream().sorted(getComparator(sort)).toList();
        List<ProductResDto> productResDtoList = productsList.stream().map(product -> {
            List<ProductImages> productImages = product.getProductImages().stream().filter(ProductImages::getIsPrimary).toList();
            return new ProductResDto().builder()
                    .id(product.getId())
                    .name(product.getName())
                    .slug(product.getSlug())
                    .shortDescription(product.getShortDescription())
                    .basePrice(product.getPrices().getBasePrice())
                    .salePrice(product.getPrices().getSalePrice())
                    .currency(product.getPrices().getCurrency())
                    .primaryImage(new ImageDto(productImages.get(0).getAltText(), productImages.get(0).getUrl()))
                    .brand(new BrandDto(product.getBrand().getId(), product.getBrand().getName()))
                    .rating(product.getSeller().getRating())
                    .reviewCount(product.getReviewsList().size())
                    .createdAt(product.getCreatedAt())
                    .build();
        }).toList();


        ProductCategories productCategories = result.getContent().get(0);
        Categories category = productCategories.getCategory();
        CategoryDto categoryDto = CategoryDto.builder().categoryId(category.getId())
                .name(category.getName())
                .slug(category.getSlug())
                .description(category.getDescription())
                .level(category.getLevel())
                .imageUrl(category.getImageUrl())
                .parent(new CategoryDto(category.getParent().getSlug(), category.getParent().getName(), category.getParent().getId()))
                .build();

        PaginationDto paginationDto = new PaginationDto(result.getTotalElements(), result.getTotalPages(), page + 1, perPage);

        ProductCategoryResDto productCategoryResDto = new ProductCategoryResDto(categoryDto, productResDtoList, paginationDto);

        assertThat(result.getTotalElements()).isEqualTo(3);
    }

    public static Comparator<Products> getComparator(String sort) {
        Comparator<Products> comparator;
        String[] parts = sort.split(":");
        String sortField = parts[0];
        String desc = parts[1];
        comparator = switch (sortField) {
            case "createdAt" -> Comparator.comparing(Products::getCreatedAt);
            case "name" -> Comparator.comparing(Products::getName);
            case "price" -> Comparator.comparing(p -> p.getPrices().getBasePrice());
            default -> Comparator.comparing(Products::getCreatedAt); // 기본값
        };
        return "desc".equals(desc) ? comparator.reversed() : comparator;
    }

    private Sort parseSort(String sortParam) {
        if (sortParam == null || sortParam.isEmpty()) {
            return Sort.unsorted();
        }

        String[] parts = sortParam.split(":");
        String property = parts[0];
        Sort.Direction direction = Sort.Direction.ASC;

        if (parts.length > 1 && parts[1].equalsIgnoreCase("desc")) {
            direction = Sort.Direction.DESC;
        }

        return Sort.by(direction, property);
    }

}