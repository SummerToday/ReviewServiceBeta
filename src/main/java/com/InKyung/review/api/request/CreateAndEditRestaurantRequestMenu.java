package com.InKyung.review.api.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class CreateAndEditRestaurantRequestMenu {
    private final String name; // 맛집 메뉴 이름
    private final Integer price; // 맛집 메뉴 가격
}
