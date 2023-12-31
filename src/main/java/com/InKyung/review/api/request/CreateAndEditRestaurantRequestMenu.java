package com.InKyung.review.api.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class CreateAndEditRestaurantRequestMenu {
    private final String name;
    private final Integer price;
}
