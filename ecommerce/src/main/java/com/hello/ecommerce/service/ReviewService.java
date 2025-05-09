package com.hello.ecommerce.service;

import com.hello.ecommerce.dto.PaginationDto;
import com.hello.ecommerce.dto.ReviewDto;
import com.hello.ecommerce.dto.UserDto;
import com.hello.ecommerce.dto.req.ReviewReqDto;
import com.hello.ecommerce.dto.req.ReviewSaveReqDto;
import com.hello.ecommerce.dto.res.ReviewDetailResDto;
import com.hello.ecommerce.dto.res.ReviewResDto;
import com.hello.ecommerce.entity.Reviews;
import com.hello.ecommerce.repository.ReviewRepository;
import com.hello.ecommerce.repository.custom.ReviewCustomRepository;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewCustomRepository reviewCustomRepository;

    public ReviewResDto findReviewsByCondition(ReviewReqDto dto) {
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

        return new ReviewResDto(reviewDetailResDtoList, summary, paginationDto);
    }

    public void save(ReviewSaveReqDto dto) {

    }


}
