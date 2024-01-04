package com.InKyung.review.repository;

import com.InKyung.review.model.QReviewEntity;
import com.InKyung.review.model.ReviewEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;


import java.util.List;


@RequiredArgsConstructor
@Repository
public class ReviewRepositoryImpl implements ReviewRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    public Double getAvgScoreByRestaurantId(Long restaurantId) {
        return queryFactory.select(QReviewEntity.reviewEntity.score.avg())
                .from(QReviewEntity.reviewEntity)
                .where(QReviewEntity.reviewEntity.restaurantId.eq(restaurantId))
                .fetchFirst();  // 특정 레스토랑에 속한 리뷰들의 평균 점수를 계산하여 첫 번째 결과를 가져오는 쿼리를 생성
    }

    @Override
    public Slice<ReviewEntity> findSliceByRestaruantId(Long restaurantId, Pageable page) {
        List<ReviewEntity> reviews = queryFactory.select(QReviewEntity.reviewEntity)
                .from(QReviewEntity.reviewEntity)
                .where(QReviewEntity.reviewEntity.restaurantId.eq(restaurantId))
                .offset((long) page.getPageNumber() * page.getPageSize()) // 몇번부터 가져올지
                .limit(page.getPageSize() + 1) // 몇개를 가져올지 but. 요청된 부분을 가져오고 난 후 다음 부분이 남아있는지 여부를 클라이언트에게
                // 고지해줘야함. so-> 클라이언트가 다음 부분을 요청 여부를 결정할 수 있음. so => +1과 을 reviews.size() > page.getPageSize() 해주는 것.
                .fetch();

        return new SliceImpl<>(
                reviews.stream().limit(page.getPageSize()).toList(),
                page,
                reviews.size() > page.getPageSize() // 다음 부분을 가져올 수 있는 지 여부 확인 가능.
        );
    }
}
/*
page.getPageNumber():
이 메서드는 현재 페이지의 번호를 반환합니다. 페이지 번호는 0부터 시작하므로 첫 번째 페이지는 0, 두 번째 페이지는 1, 그리고 이런 식으로 계속됨.

page.getPageSize():
이 메서드는 페이지당 항목 수, 즉 한 페이지에 표시되는 항목의 개수를 반환. 이는 페이지 크기를 나타냄.
 */
