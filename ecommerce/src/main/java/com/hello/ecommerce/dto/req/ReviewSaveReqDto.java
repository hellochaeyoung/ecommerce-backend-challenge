package com.hello.ecommerce.dto.req;

import com.hello.ecommerce.entity.Products;
import com.hello.ecommerce.entity.Reviews;
import com.hello.ecommerce.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter @Setter
public class ReviewSaveReqDto {

    private Long productId;
    private Long userId;
    private Double rating;
    private String title;
    private String content;
    private Long reviewId;

    public ReviewSaveReqDto(Double rating, String title, String content) {
        this.rating = rating;
        this.title = title;
        this.content = content;
    }

    public ReviewSaveReqDto(Long productId, Long userId, Double rating, String title, String content) {
        this.productId = productId;
        this.userId = userId;
        this.rating = rating;
        this.title = title;
        this.content = content;
    }

    public Reviews toEntity(Products products, Users users) {
        return Reviews.builder()
                .rating(rating)
                .title(title)
                .content(content)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .verifiedPurchase(true)
                .helpfulVotes(0)
                .products(products)
                .user(users)
                .build();
    }
}
