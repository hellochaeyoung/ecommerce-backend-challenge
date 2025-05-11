package com.hello.ecommerce.service;

import com.hello.ecommerce.dto.PaginationDto;
import com.hello.ecommerce.dto.ReviewDto;
import com.hello.ecommerce.dto.UserDto;
import com.hello.ecommerce.dto.req.ReviewReqDto;
import com.hello.ecommerce.dto.req.ReviewSaveReqDto;
import com.hello.ecommerce.dto.res.ReviewDetailResDto;
import com.hello.ecommerce.dto.res.ReviewResDto;
import com.hello.ecommerce.entity.Products;
import com.hello.ecommerce.entity.Reviews;
import com.hello.ecommerce.entity.Users;
import com.hello.ecommerce.repository.ProductsRepository;
import com.hello.ecommerce.repository.ReviewRepository;
import com.hello.ecommerce.repository.UsersRepository;
import com.hello.ecommerce.repository.custom.ReviewCustomRepository;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

public interface ReviewService {
    ReviewResDto findReviewsByCondition(ReviewReqDto dto);

    ReviewDetailResDto save(ReviewSaveReqDto dto);

    void update(ReviewSaveReqDto dto);

    void delete(Long reviewId, Long userId);
}
