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
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductsRepository productsRepository;
    private final UsersRepository usersRepository;

    public ReviewResDto findReviewsByCondition(ReviewReqDto dto) {
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

        return new ReviewResDto(reviewDetailResDtoList, summary, paginationDto);
    }

    public ReviewDetailResDto save(ReviewSaveReqDto dto) {
        Products products = productsRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("상품 정보가 없습니다."));

        Users users = usersRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("회원 정보가 없습니다."));

        Reviews review = dto.toEntity(products, users);

        Reviews saved = reviewRepository.save(review);

        return new ReviewDetailResDto().builder()
                .id(saved.getId())
                .user(new UserDto(users.getId(), users.getName(), users.getAvatarUrl()))
                .rating(saved.getRating())
                .title(saved.getTitle())
                .content(saved.getContent())
                .createdAt(saved.getCreatedAt())
                .updatedAt(saved.getUpdatedAt())
                .verifiedPurchase(saved.getVerifiedPurchase())
                .helpfulVotes(saved.getHelpfulVotes())
                .build();
    }

    public void update(ReviewSaveReqDto dto) {
        Reviews reviews = reviewRepository.findById(dto.getReviewId())
                .orElseThrow(() -> new RuntimeException("리뷰 정보가 없습니다."));

        reviews.setRating(dto.getRating());
        reviews.setTitle(dto.getTitle());
        reviews.setContent(dto.getContent());
        reviews.setUpdatedAt(LocalDateTime.now());
    }

    public void delete(Long reviewId, Long userId) {
        Users users = usersRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("회원 정보가 없습니다."));
        int result = reviewRepository.deleteByIdAndUser(reviewId, users);

        if (result == 0) {
            throw new RuntimeException("리뷰가 없거나 다른 사용자의 리뷰를 삭제할 권한이 없습니다.");
        }
    }


}
