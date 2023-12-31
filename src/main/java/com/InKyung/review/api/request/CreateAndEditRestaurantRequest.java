package com.InKyung.review.api.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CreateAndEditRestaurantRequest {
    private final String name;
    private final String address;
    private final List<CreateAndEditRestaurantRequestMenu> menus;
}
