package com.hello.ecommerce.controller;

import com.hello.ecommerce.dto.req.ReviewSaveReqDto;
import com.hello.ecommerce.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PutMapping("/{id}")
    public void updateReview(@PathVariable Long id, @RequestBody ReviewSaveReqDto dto) {
        dto.setReviewId(id);
        reviewService.update(dto);
    }

    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable Long id, @RequestParam Long userId) {
        reviewService.delete(id, userId);
    }

}
