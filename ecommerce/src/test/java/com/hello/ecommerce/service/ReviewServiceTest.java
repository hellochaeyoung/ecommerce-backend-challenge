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
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReviewServiceTest {

    private static final Logger log = LoggerFactory.getLogger(ReviewServiceTest.class);

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Test
    void 리뷰_조회() {
        ReviewReqDto dto = new ReviewReqDto(20L, 0, 10, null, 2);
        PageImpl<Reviews> reviews = reviewRepository.findByProduct(dto);

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

    @Test
    @Transactional
    void 리뷰_저장() {
        Long userId = 1L;
        Long productId = 2L;
        int rating = 5;
        String title = "완벽한 소파입니다!";
        String content = "배송도 빠르고 품질도 매우 좋습니다. 색상도 사진과 동일하고 조립도 쉬웠어요.";

        ReviewSaveReqDto dto = new ReviewSaveReqDto(productId, userId, (double) rating, title, content);

        Products products = productsRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("상품 정보가 없습니다."));

        int originSize = products.getReviewsList().size();

        Users users = usersRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("회원 정보가 없습니다."));

        Reviews review = dto.toEntity(products, users);

        Reviews saved = reviewRepository.save(review);

        List<Reviews> byProducts = reviewRepository.findByProducts(products);

        assertThat(originSize + 1).isEqualTo(byProducts.size());
    }

    @Test
    @Transactional
    @Rollback(value = false)
    void 리뷰_수정() {
        Long reviewId = 11L;

        double rating = 4;
        String title = "좋은 소파입니다!";
        String content = "배송도 빠르고 품질도 좋습니다. 다만 색상이 사진보다 약간 어둡습니다.";

        Reviews reviews = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("리뷰 정보가 없습니다."));

        reviews.setRating(rating);
        reviews.setTitle(title);
        reviews.setContent(content);
        reviews.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    @Transactional
    @Rollback(value = false)
    void 리뷰_삭제_예외() {
        Long reviewId = 94L;
        Long userId = 4L;

        Users users = usersRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("회원 정보가 없습니다."));
        int result = reviewRepository.deleteByIdAndUser(reviewId, users);

        assertThat(result).isEqualTo(0);
    }
}