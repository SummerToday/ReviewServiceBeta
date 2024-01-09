package com.InKyung.review.api.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CreateAndEditRestaurantRequest {
    private final String name; // 맛집 이름
    private final String address; //맛집 주소
    private final List<CreateAndEditRestaurantRequestMenu> menus; // 맛집 메뉴 리스트
}
