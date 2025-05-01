package com.hello.ecommerce.repository.custom;

import com.hello.ecommerce.dto.BrandDto;
import com.hello.ecommerce.dto.ImageDto;
import com.hello.ecommerce.dto.SellerDto;
import com.hello.ecommerce.dto.req.ProductListReqDto;
import com.hello.ecommerce.dto.res.ProductResDto;
import com.hello.ecommerce.entity.*;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ProductCustomRepository {

    private final JPAQueryFactory queryFactory;

    private final QProducts products = QProducts.products;

    private final QProductPrices productPrices = QProductPrices.productPrices;
    private final QProductCategories productCategories = QProductCategories.productCategories;
    private final QSellers sellers = QSellers.sellers;
    private final QBrands brands = QBrands.brands;
    private final QProductOptions productOptions = QProductOptions.productOptions;
    private final QProductOptionGroups productOptionGroups = QProductOptionGroups.productOptionGroups;
    private final QProductImages productImages = QProductImages.productImages;
    private final QReviews reviews = QReviews.reviews;

    public Page<ProductResDto> findProductsByCondition(ProductListReqDto dto, Pageable pageable) {
        List<Long> categoryList = Arrays.stream(dto.getCategory()).asLongStream().boxed().toList();
        List<ProductResDto> result = queryFactory
                .selectDistinct(
                        Projections.constructor(ProductResDto.class,
                                products.id,
                                products.name,
                                products.slug,
                                products.shortDescription,
                                productPrices.basePrice,
                                productPrices.salePrice,
                                productPrices.currency,
                                Projections.constructor(ImageDto.class, productImages.url, productImages.altText),
                                Projections.constructor(BrandDto.class, brands.id, brands.name),
                                Projections.constructor(SellerDto.class, sellers.id, sellers.name),
                                reviews.rating.avg(),
                                products.reviewsList.size(),
                                new CaseBuilder().when(productOptions.stock.gt(0)).then(true).otherwise(false),
                                products.status,
                                products.createdAt))
                .from(products)
                .join(productPrices).on(products.id.eq(productPrices.product.id))
                .join(productCategories).on(products.id.eq(productCategories.products.id))
                .join(sellers).on(products.seller.id.eq(sellers.id))
                .join(brands).on(products.brand.id.eq(brands.id))
                .join(productOptionGroups).on(productOptionGroups.products.id.eq(products.id))
                .join(productOptions).on(productOptions.productOptionGroup.id.eq(productOptionGroups.id))
                .join(productImages).on(products.id.eq(productImages.products.id))
                .join(reviews).on(products.id.eq(reviews.products.id))
                .where(products.status.eq(dto.getStatus()),
                        productPrices.basePrice.between(dto.getMinPrice(), dto.getMaxPrice()),
                        productCategories.category.id.in(categoryList),
                        sellers.id.eq((long) dto.getSeller()),
                        brands.id.eq((long) dto.getBrand()),
                        checkStock(dto.isInStock()),
                        products.name.contains(dto.getSearch()))
                .orderBy(getOrderCondition(dto.getSort()))
                .groupBy(products.id, products.name, products.slug, products.shortDescription, productPrices.basePrice,
                        productPrices.salePrice,
                        productPrices.currency,productImages.url, productImages.altText,
                        brands.id, brands.name, sellers.id, sellers.name, products.reviewsList.size(),
                        productOptions.stock,
                        products.status, products.createdAt)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> count = queryFactory
                .select(products.id.countDistinct())
                .from(products)
                .join(productPrices).on(products.id.eq(productPrices.product.id))
                .join(productCategories).on(products.id.eq(productCategories.products.id))
                .join(sellers).on(products.seller.id.eq(sellers.id))
                .join(brands).on(products.brand.id.eq(brands.id))
                .join(productOptionGroups).on(products.productOptionGroups.contains(productOptionGroups))
                .join(productOptions).on(productOptions.productOptionGroup.id.eq(productOptionGroups.id))
                .join(reviews).on(products.id.eq(reviews.products.id))
                .where(
                        products.status.eq(dto.getStatus()),
                        productPrices.basePrice.between(dto.getMinPrice(), dto.getMaxPrice()),
                        productCategories.category.id.in(categoryList),
                        sellers.id.eq((long) dto.getSeller()),
                        brands.id.eq((long) dto.getBrand()),
                        checkStock(dto.isInStock()),
                        products.name.contains(dto.getSearch())
                );

        return PageableExecutionUtils.getPage(result, pageable, () -> count.fetchOne());


    }

    public BooleanBuilder checkStock(boolean isStock) {
        BooleanBuilder builder = new BooleanBuilder();
        return isStock ? builder.and(productOptions.stock.gt(0)) : builder;
    }

    public OrderSpecifier getOrderCondition(String sort) {
        String[] condition = sort.split(":");
        String sortField = toCamelCase(condition[0]);
        PathBuilder orderExpression = new PathBuilder(Products.class, "products");
        return condition.length > 1 && condition[1].equals("desc") ? new OrderSpecifier<>(Order.DESC, orderExpression.get(sortField))
                : new OrderSpecifier<>(Order.ASC, orderExpression.get(sortField));
    }

    private String toCamelCase(String snake) {
        String[] parts = snake.split("_");
        return parts[0] + Arrays.stream(parts, 1, parts.length)
                .map(s -> s.substring(0,1).toUpperCase() + s.substring(1))
                .collect(Collectors.joining());
    }
}
