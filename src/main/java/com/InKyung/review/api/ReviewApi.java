package com.InKyung.review.api;

import com.InKyung.review.api.request.CreateReviewRequest;
import com.InKyung.review.service.ReviewService;
import com.InKyung.review.service.dto.ReviewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ReviewApi {
    private final ReviewService reviewService;

    @PostMapping("/review") // 리뷰 생성
    public void createReview(
            @RequestBody CreateReviewRequest request
    ){
        reviewService.createReview(request.getRestaurantId(), request.getContent(), request.getScore());
    }

    @DeleteMapping("/review/{reviewId}") // 특정 리뷰 삭제
    public void deleteReview(
            @PathVariable("reviewId") Long reviewId
    ){
        reviewService.deleteReview(reviewId);
    }

    @GetMapping("/restaurant/{restaurantId}/reviews") // 리뷰 조회
    public ReviewDto getRestaurantReviews(
            @PathVariable("restaurantId") Long restaurantId,
            @RequestParam("offset") Integer offset, // 리스트 시작 지점
            @RequestParam("limit") Integer limit // 한 페이지에 보여질 리스트 개수
    ){
        return reviewService.getRestaurantReview(restaurantId, PageRequest.of(offset/limit, limit));
        // PageRequest 클래스를 사용하여 페이지네이션을 위한 Pageable 객체를 생성, offset과 limit을 사용하여 페이지 번호와 페이지 크기를 계산하여 PageRequest를 생성합니다.
        // PageRequest에 의해 Pageable에 페이징 정보가 담겨 객체화 된다.PageRequest에 의해 Pageable에 페이징 정보가 담겨 객체화 된다.
    }
}
