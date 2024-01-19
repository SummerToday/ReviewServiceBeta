package com.InKyung.review.api.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreateReviewRequest {
    private final Long restaurantId; // 리뷰를 등록할 맛집
    private final String content; // 리뷰 내용
    private final Double score; // 리뷰 점수
}
