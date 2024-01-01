package com.InKyung.review.api;

import com.InKyung.review.api.request.CreateAndEditRestaurantRequest;
import com.InKyung.review.model.RestaurantEntity;
import com.InKyung.review.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
@RestController
@RequiredArgsConstructor
public class RestaurantApi {

    private final RestaurantService restaurantService;
    @GetMapping("/restaurants")
    public String getRestaurants(){
        return "This is getRestaurants";
    }

    @GetMapping("/restaurant/{restaurantId}")
    public String getRestaurant(
            @PathVariable Long restaurantId
    ){
        return "This is getRestaurant, " + restaurantId;
    }

    @PostMapping("/restaurant")
    public void createRestaurant(
            @RequestBody CreateAndEditRestaurantRequest request
            ){
        restaurantService.createRestaurant(request);
    }

    @PutMapping("/restaurant/{restaurantId}")
    public void editRestaurant(
            @PathVariable Long restaurantId,
            @RequestBody CreateAndEditRestaurantRequest request
    ){
       restaurantService.editRestaurant(restaurantId, request);
    }

    @DeleteMapping("/restaurant/{restaurantId}")
    public void deleteRestaurant(
            @PathVariable Long restaurantId
    ){
        restaurantService.deleteRestaurant(restaurantId);
    }

}
