package com.hello.ecommerce.repository.custom;

import com.hello.ecommerce.dto.BrandDto;
import com.hello.ecommerce.dto.ImageDto;
import com.hello.ecommerce.dto.SellerDto;
import com.hello.ecommerce.dto.req.ProductListReqDto;
import com.hello.ecommerce.dto.req.ProductSearchReqDto;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ProductCustomRepositoryImpl implements ProductCustomRepository{

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
    private final JPAQueryFactory jpaQueryFactory;

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

    public PageImpl<Products> findProductsBySearch(ProductSearchReqDto dto) {
        BooleanBuilder builder = getBooleanBuilder(dto);

        OrderSpecifier<?> orderSpecifier = getOrderSpecifier(dto.getKeyword(), dto.getSort(), products); // 아래 참고

        List<Products> result = queryFactory
                .selectFrom(products)
                .join(products.brand, brands).fetchJoin()
                .join(products.seller, sellers).fetchJoin()
                .join(products.prices, productPrices).fetchJoin()
                .join(products.categories, productCategories).fetchJoin()
                .join(products.productOptionGroups, productOptionGroups)
                .join(productOptionGroups.productOptions, productOptions)
                .where(builder)
                .orderBy(orderSpecifier)
                .offset((long) dto.getPage() * dto.getPerPage())
                .limit(dto.getPerPage())
                .fetch();

        Long total = queryFactory
                .select(products.countDistinct())
                .from(products)
                .join(products.brand, brands)
                .join(products.seller, sellers)
                .join(products.prices, productPrices)
                .join(products.categories, productCategories)
                .join(products.productOptionGroups, productOptionGroups)
                .join(productOptionGroups.productOptions, productOptions)
                .where(builder)
                .fetchOne();

        return new PageImpl<>(result, PageRequest.of(dto.getPage(), dto.getPerPage()), total);

    }

    public List<Products> findPopularProducts(int limit) {
        return jpaQueryFactory.selectFrom(products)
                .join(products.seller, sellers).fetchJoin()
                .orderBy(sellers.rating.desc())
                .limit(limit)
                .fetch();
    }

    public List<Tuple> findFeaturedCategories(int limit) {
        return jpaQueryFactory
                .select(
                        productCategories.category.id,
                        productCategories.category.name,
                        productCategories.category.slug,
                        productCategories.category.imageUrl,
                        products.count())
                .from(products)
                .join(products.categories, productCategories)
                .groupBy(productCategories.category.id,
                        productCategories.category.name,
                        productCategories.category.slug,
                        productCategories.category.imageUrl)
                .orderBy(products.count().desc())
                .limit(limit)
                .fetch();
    }


    private BooleanBuilder getBooleanBuilder(ProductSearchReqDto dto) {
        BooleanBuilder builder = new BooleanBuilder();

        if (dto.getKeyword() != null && !dto.getKeyword().isBlank()) {
            builder.and(
                    products.name.containsIgnoreCase(dto.getKeyword())
                            .or(products.slug.containsIgnoreCase(dto.getKeyword()))
            );
        }

        List<Long> categoryIds = Arrays.stream(dto.getCategory())
                .mapToLong(i -> i)       // int → long
                .boxed()
                .collect(Collectors.toList());
        if (dto.getCategory() != null && dto.getCategory().length > 0) {
            builder.and(productCategories.category.id.in(categoryIds));
        }

        List<Long> brandIds = Arrays.stream(dto.getBrand())
                .mapToLong(i -> i)       // int → long
                .boxed()
                .collect(Collectors.toList());
        if (dto.getBrand() != null && dto.getBrand().length > 0) {
            builder.and(products.brand.id.in(brandIds));
        }


        List<Long> sellerIds = Arrays.stream(dto.getSeller())
                .mapToLong(i -> i)       // int → long
                .boxed()
                .collect(Collectors.toList());
        if (dto.getSeller() != null && dto.getSeller().length > 0) {
            builder.and(products.seller.id.in(sellerIds));
        }

        if (dto.isInStock()) {
            builder.and(productOptions.stock.gt(0));
        }

        builder.and(products.prices.basePrice.between(dto.getMinPrice(), dto.getMaxPrice()));

        builder.and(products.seller.rating.goe(dto.getRating()));
        return builder;
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

    public OrderSpecifier<?> getOrderSpecifier(String keyword, String sort, QProducts products) {
        if (sort == null || sort.isBlank()) {
            return products.createdAt.desc(); // 기본값
        }

        String[] parts = sort.split(":");
        String field = parts[0];
        boolean desc = parts.length > 1 && parts[1].equalsIgnoreCase("desc");

        switch (field) {
            case "createdAt":
                return desc ? products.createdAt.desc() : products.createdAt.asc();
            case "price":
                return desc ? products.prices.basePrice.desc() : products.prices.basePrice.asc();
            case "name":
                return desc ? products.name.desc() : products.name.asc();
            case "relevance":
                return new CaseBuilder()
                        .when(products.name.containsIgnoreCase(keyword)).then(1)
                        .when(products.slug.containsIgnoreCase(keyword)).then(2)
                        .otherwise(3).asc();
            default:
                return products.createdAt.desc();
        }
    }
}
