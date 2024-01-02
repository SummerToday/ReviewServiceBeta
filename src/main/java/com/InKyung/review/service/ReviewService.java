package com.InKyung.review.service;

import com.InKyung.review.model.ReviewEntity;
import com.InKyung.review.repository.RestaurantRepository;
import com.InKyung.review.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final RestaurantRepository restaurantRepository;
    private final ReviewRepository reviewRepository;

    @Transactional //생성, 수정, 삭제 기능은 해당 어노테이션 사용해줌.
    public void createReview(Long restaurantId, String content, Double score){
        restaurantRepository.findById(restaurantId).orElseThrow(); // 없는 맛집이라면 에러 발생.

        ReviewEntity review = ReviewEntity.builder()
                .restaurantId(restaurantId)
                .content(content)
                .score(score)
                .createdAt(ZonedDateTime.now())
                .build();

        reviewRepository.save(review); // DB에 저장.
    }
    @Transactional
    public void deleteReview(Long reviewId){
        ReviewEntity review = reviewRepository.findById(reviewId).orElseThrow(); // oreElseThrow() -> 없는 리뷰는 삭제할 수 없기 때문에

        reviewRepository.delete(review);

    }
}
