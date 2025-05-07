package com.hello.ecommerce.dto;

import com.hello.ecommerce.entity.Reviews;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

@AllArgsConstructor
public class ReviewDto {

    private Double average;
    private int count;
    private Map<Integer, Long> distribution;

    public ReviewDto(List<Reviews> reviewsList) {
        this.average = getAverage(reviewsList);
        this.count = reviewsList.size();
        this.distribution = getDistribution(reviewsList);
    }

    private Double getAverage(List<Reviews> reviewsList) {
        return reviewsList.stream().map(Reviews::getRating)
                .mapToDouble(Double::doubleValue).average().orElse(0);
    }

    private Map<Integer, Long> getDistribution(List<Reviews> reviewsList) {
        return reviewsList.stream()
                .map(r -> (int) Math.floor(r.getRating())) // 소수점 아래 버림
                .filter(r -> r >= 1 && r <= 5) // 1~5점만 유효
                .collect(Collectors.groupingBy(
                        r -> r,
                        Collectors.counting()
                ));
    }
}
