package com.hello.ecommerce.service;

import com.hello.ecommerce.dto.PaginationDto;
import com.hello.ecommerce.dto.ReviewDto;
import com.hello.ecommerce.dto.UserDto;
import com.hello.ecommerce.dto.req.ReviewReqDto;
import com.hello.ecommerce.dto.res.ReviewDetailResDto;
import com.hello.ecommerce.dto.res.ReviewResDto;
import com.hello.ecommerce.entity.Reviews;
import com.hello.ecommerce.repository.ReviewRepository;
import com.hello.ecommerce.repository.custom.ReviewCustomRepository;
import com.querydsl.core.Tuple;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReviewServiceTest {

    @Autowired
    private ReviewCustomRepository reviewCustomRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    void 리뷰_조회() {
        ReviewReqDto dto = new ReviewReqDto(20L, 0, 10, null, 2);
        PageImpl<Reviews> reviews = reviewCustomRepository.findByProduct(dto);

        List<ReviewDetailResDto> reviewDetailResDtoList = reviews.getContent().stream().map(review -> new ReviewDetailResDto().builder()
                .id(review.getId())
                .user(new UserDto(review.getUser().getId(), review.getUser().getName(), review.getUser().getAvatarUrl()))
                .rating(review.getRating())
                .title(review.getTitle())
                .content(review.getContent())
                .createdAt(review.getCreatedAt())
                .updatedAt(review.getUpdatedAt())
                .verifiedPurchase(review.getVerifiedPurchase())
                .helpfulVotes(review.getHelpfulVotes())
                .build()).toList();

        ReviewDto summary = new ReviewDto(reviews.getContent());

        PaginationDto paginationDto = new PaginationDto(reviews.getTotalElements(), reviews.getTotalPages(), dto.getPage(), dto.getPerPage());

        ReviewResDto result = new ReviewResDto(reviewDetailResDtoList, summary, paginationDto);
    }
}