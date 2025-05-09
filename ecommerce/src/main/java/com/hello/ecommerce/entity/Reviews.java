package com.hello.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
@Getter
public class Reviews {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double rating;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean verifiedPurchase;
    private Integer helpfulVotes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Products products;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;
}
