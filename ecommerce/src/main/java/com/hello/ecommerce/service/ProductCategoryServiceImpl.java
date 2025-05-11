package com.hello.ecommerce.service;

import com.hello.ecommerce.dto.BrandDto;
import com.hello.ecommerce.dto.CategoryDto;
import com.hello.ecommerce.dto.ImageDto;
import com.hello.ecommerce.dto.PaginationDto;
import com.hello.ecommerce.dto.req.ProductCategoryReqDto;
import com.hello.ecommerce.dto.res.ProductCategoryResDto;
import com.hello.ecommerce.dto.res.ProductResDto;
import com.hello.ecommerce.entity.Categories;
import com.hello.ecommerce.entity.ProductCategories;
import com.hello.ecommerce.entity.ProductImages;
import com.hello.ecommerce.entity.Products;
import com.hello.ecommerce.repository.ProductCategoriesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductCategoryServiceImpl implements ProductCategoryService {

    private final ProductCategoriesRepository productCategoriesRepository;

    public ProductCategoryResDto findProductsByCategoryId(ProductCategoryReqDto dto) {
        Pageable pageable = PageRequest.of(dto.getPage(), dto.getPerPage());
        Page<ProductCategories> result = productCategoriesRepository.findByCategoryId(dto.getCategoryId(), pageable);

        CategoryDto categoryDto = getCategoryDto(result);
        List<ProductResDto> productResDtoList = getProductResDtos(dto, result);
        PaginationDto paginationDto
                = new PaginationDto(result.getTotalElements(), result.getTotalPages(), dto.getPage() + 1, dto.getPerPage());

        return new ProductCategoryResDto(categoryDto, productResDtoList, paginationDto);

    }

    private List<ProductResDto> getProductResDtos(ProductCategoryReqDto dto, Page<ProductCategories> result) {
        List<Products> productsList = result.getContent().stream().map(ProductCategories::getProducts).toList();
        productsList = productsList.stream().sorted(getComparator(dto.getSort())).toList();
        return productsList.stream().map(product -> {
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
    }

    private CategoryDto getCategoryDto(Page<ProductCategories> result) {
        ProductCategories productCategories = result.getContent().get(0);
        Categories category = productCategories.getCategory();
        return CategoryDto.builder().categoryId(category.getId())
                .name(category.getName())
                .slug(category.getSlug())
                .description(category.getDescription())
                .level(category.getLevel())
                .imageUrl(category.getImageUrl())
                .parent(new CategoryDto(category.getParent().getSlug(), category.getParent().getName(), category.getParent().getId()))
                .build();
    }

    private static Comparator<Products> getComparator(String sort) {
        Comparator<Products> comparator;
        String[] parts = sort.split(":");
        String sortField = parts[0];
        String desc = parts[1];
        comparator = switch (sortField) {
            case "createdAt" -> Comparator.comparing(Products::getCreatedAt);
            case "name" -> Comparator.comparing(Products::getName);
            case "price" -> Comparator.comparing(p -> p.getPrices().getBasePrice());
            default -> Comparator.comparing(Products::getCreatedAt);
        };
        return "desc".equals(desc) ? comparator.reversed() : comparator;
    }
}
