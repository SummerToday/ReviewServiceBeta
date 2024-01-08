package com.InKyung.review.api;

import com.InKyung.review.api.request.CreateAndEditRestaurantRequest;
import com.InKyung.review.api.response.RestaurantDetailView;
import com.InKyung.review.api.response.RestaurantView;
import com.InKyung.review.service.RestaurantService;
import lombok.RequiredArgsConstructor;
/*
lombok.RequiredArgsConstructor는 Lombok 라이브러리의 어노테이션 중 하나로, 클래스의 필드를 기반으로 생성자를 자동으로 생성해주는 기능 제공.
=> 클래스에 선언된 final이나 @NonNull 어노테이션이 붙은 필드들을 인자로 받는 생성자가 자동으로 추가.
*/
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController // @Controller + @ResponseBody 결합된 어노테이션.
/*
@Controller -> view를 반환하기 위해 사용 but. 데이터를 반환하기 위해서는 @ResponseBody 어노테이션을 사용해주어야 JSON 형태로 데이터 반환 가능.
@RestController -> JSON 형태로 객체 데이터를 반환하는 것이 주용도.
 */
@RequiredArgsConstructor
public class RestaurantApi {

    private final RestaurantService restaurantService;
    @GetMapping("/restaurants") // 맛집 리스트 가져오기
    public List<RestaurantView> getRestaurants(){
        return restaurantService.getAllRestaurants();
    }

    @GetMapping("/restaurant/{restaurantId}") // 맛집 정보 가져오기
    public RestaurantDetailView getRestaurant(
            @PathVariable Long restaurantId //@PathVariable -> 경로 변수를 표시하기 위해 매개변수에 사용. {id}로 둘러싸인 값.
            //경로 변수는 반드시 값을 가져야 하며, 값이 없는 경우 404 오류 발생. 상세 조회, 수정, 삭제와 같은 작업에서 리소스 식별자로 사용.
    ){
        return restaurantService.getRestaurantDetail(restaurantId);
    }

    @PostMapping("/restaurant") // 맛집 정보 생성
    public void createRestaurant(
            @RequestBody CreateAndEditRestaurantRequest request
            ){
        restaurantService.createRestaurant(request);
    }

    @PutMapping("/restaurant/{restaurantId}") // 맛집 정보 수정
    public void editRestaurant(
            @PathVariable Long restaurantId,
            @RequestBody CreateAndEditRestaurantRequest request
    ){
       restaurantService.editRestaurant(restaurantId, request);
    }

    @DeleteMapping("/restaurant/{restaurantId}") // 맛집 정보 삭제
    public void deleteRestaurant(
            @PathVariable Long restaurantId
    ){
        restaurantService.deleteRestaurant(restaurantId);
    }

}
/*
공통적인 URL 부분은 상위 클래스에 @RequestMapping을 사용하여 따로 뺴줄 수 있음.
 */
