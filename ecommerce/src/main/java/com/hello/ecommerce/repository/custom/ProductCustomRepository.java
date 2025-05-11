package com.hello.ecommerce.repository.custom;

import com.hello.ecommerce.dto.BrandDto;
import com.hello.ecommerce.dto.ImageDto;
import com.hello.ecommerce.dto.SellerDto;
import com.hello.ecommerce.dto.req.ProductListReqDto;
import com.hello.ecommerce.dto.req.ProductSearchReqDto;
import com.hello.ecommerce.dto.res.ProductResDto;
import com.hello.ecommerce.entity.*;
import com.hello.ecommerce.repository.ProductsRepository;
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

public interface ProductCustomRepository {

    Page<ProductResDto> findProductsByCondition(ProductListReqDto dto, Pageable pageable);

    PageImpl<Products> findProductsBySearch(ProductSearchReqDto dto);

    List<Products> findPopularProducts(int limit);

    List<Tuple> findFeaturedCategories(int limit);
}
