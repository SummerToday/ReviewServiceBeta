package com.InKyung.review.service;

import com.InKyung.review.model.ReviewEntity;
import com.InKyung.review.repository.RestaurantRepository;
import com.InKyung.review.repository.ReviewRepository;
import com.InKyung.review.service.dto.ReviewDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;


import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final RestaurantRepository restaurantRepository;
    private final ReviewRepository reviewRepository;

    @Transactional //생성, 수정, 삭제 기능은 해당 어노테이션 사용해줌.
    public void createReview(Long restaurantId, String content, Double score){ // 리뷰 생성
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
    public void deleteReview(Long reviewId){ // 리뷰 삭제
        ReviewEntity review = reviewRepository.findById(reviewId).orElseThrow(); // oreElseThrow() -> 없는 리뷰는 삭제할 수 없기 때문에

        reviewRepository.delete(review); // 해당 ID의 리뷰 삭제

    }

    public ReviewDto getRestaurantReview(Long restaurantId, Pageable page){ // 해당 맛집의 리뷰 목록 조회
        Double avgScore = reviewRepository.getAvgScoreByRestaurantId(restaurantId); // 해당 맛집의 리뷰 평균 점수 계산.
        Slice<ReviewEntity> reviews= reviewRepository.findSliceByRestaruantId(restaurantId, page);

        return ReviewDto.builder()
                .avgScore(avgScore)
                .reviews(reviews.getContent())
                .page(
                        ReviewDto.ReviewDtoPage.builder()
                                .offset(page.getPageNumber() * page.getPageSize())
                                .limit(page.getPageSize())
                                .build()
                )
                .build();


    }


    /*
         * DTO
           : DTO(Data Transfer Object), 계층간에 비즈니스 로직을 포함하지 않는 데이터의 교환을 위해 사용하는 객체.
             예를 들면 Controller, Service, Repository, DataBase 는 서로 User 라는 도메인 객체를 통해 데이터를 전달하고,
             Controller 에서 Client 로 리턴할 때는 필요한 정보만 제공하기 위해 UserDTO 에 매핑하여 리턴하는 것
             ex. user 객체에 있는 비밀번호는 다른 클라이언트에게 제공할 필요x -> DTO 사용.


          * DTO와 Domain을 분리하는 이유
            : 관심사를 분리하기 위해.
              ex. Model-Controller-View 가 각자의 역할을 수행하여 유지보수가 편리하기 때문.
                  Controller 는 중간 다리의 역할로 client 와 request/response 하는 책임이 있고,
                  Model 은 DataBase 에서 받아온 데이터를 다루는 책임이 있음
                  so, 관심사의 분리를 통해 복잡한 시스템을 효율적으로 작동을 할 수 있음. -> DTO 사용.
              + 주요 목적 -> 한번의 호출로 여러 매개변수를 일괄 처리해서 서버의 왕복을 줄이는 것.

           * DTO 주요 정보 요약
             - DTO 를 사용하는 주요 목적은 한 번의 호출로 해당 호출에 관련 된 모든 데이터를 담은 객체를 리턴 받아 사용하는 것.
             - 네트워크 비용 > 컨트롤러에서 매번 변환하는 비용 이라고 판단한 것.
             - 이 때, 같은 필드인데 어떤 경우에는 null 이고 어떤 경우에는 값이 있다면 유지보수가 어려워짐
             - 그렇기 때문에 null 없이 모든 필드에 값이 있는 공통 DTO 를 사용.
             - 공통 DTO 가 커져서 성능에 이슈가 생기거나, 특수한 경우에만 별도의 DTO 를 사용.

     */
}
