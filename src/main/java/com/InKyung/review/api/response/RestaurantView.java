package com.InKyung.review.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;

@AllArgsConstructor
@Builder
@Getter
public class RestaurantView {
    private final Long id;  // 맛집 등록번호
    private final String name; // 맛집 이름
    private final String address; // 맛집 주소
    private final ZonedDateTime createdAt; // 맛집정보 등록일시
    private final ZonedDateTime updatedAt; // 맛집정보 수정일시
}
