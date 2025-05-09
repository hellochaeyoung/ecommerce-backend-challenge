package com.hello.ecommerce.dto.res;

import com.hello.ecommerce.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ReviewDetailResDto {

    private Long id;
    private UserDto user;
    private Double rating;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean verifiedPurchase;
    private int helpfulVotes;
}
