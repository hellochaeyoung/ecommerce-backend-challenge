package com.hello.ecommerce.repository.custom;

import com.hello.ecommerce.dto.req.ReviewReqDto;
import com.hello.ecommerce.entity.QReviews;
import com.hello.ecommerce.entity.QUsers;
import com.hello.ecommerce.entity.Reviews;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReviewCustomRepositoryImpl implements ReviewCustomRepository {

    private final JPAQueryFactory queryFactory;

    private final QReviews reviews = QReviews.reviews;
    private final QUsers users = QUsers.users;

    public PageImpl<Reviews> findByProduct(ReviewReqDto dto) {
        List<Reviews> result = queryFactory.select(reviews)
                .from(reviews)
                .join(reviews.user, users).fetchJoin()
                .where(reviews.rating.gt(dto.getRating())
                        .and(reviews.products.id.eq(dto.getProductId())))
                .orderBy(getOrderSpecifier(dto.getSort(), reviews))
                .offset((long) dto.getPage() * dto.getPerPage())
                .limit(dto.getPerPage())
                .fetch();

        Long total = queryFactory.select(reviews.countDistinct())
                .from(reviews)
                .where(reviews.rating.gt(dto.getRating())
                        .and(reviews.products.id.eq(dto.getProductId())))
                .fetchOne();

        total = total == null ? 0 : total;

        return new PageImpl<>(result, PageRequest.of(dto.getPage(), dto.getPerPage()), total);
    }

    public OrderSpecifier<?> getOrderSpecifier(String sort, QReviews reviews) {
        if (sort == null || sort.isBlank()) {
            return reviews.createdAt.desc(); // 기본값
        }

        String[] parts = sort.split(":");
        String field = parts[0];
        boolean desc = parts.length > 1 && parts[1].equalsIgnoreCase("desc");

        switch (field) {
            case "createdAt":
                return desc ? reviews.createdAt.desc() : reviews.createdAt.asc();
            case "rating":
                return desc ? reviews.rating.desc() : reviews.rating.asc();
            case "helpfulVotes":
                return desc ? reviews.helpfulVotes.desc() : reviews.helpfulVotes.asc();
            default:
                return reviews.createdAt.desc();
        }
    }
}
