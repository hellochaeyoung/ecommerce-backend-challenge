package com.hello.ecommerce.repository.custom;

import com.hello.ecommerce.dto.req.ReviewReqDto;
import com.hello.ecommerce.entity.QProducts;
import com.hello.ecommerce.entity.QReviews;
import com.hello.ecommerce.entity.QUsers;
import com.hello.ecommerce.entity.Reviews;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ReviewCustomRepository {
    PageImpl<Reviews> findByProduct(ReviewReqDto dto);
}
