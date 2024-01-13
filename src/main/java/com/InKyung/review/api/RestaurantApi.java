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
@Controller
:해당 클래스는 @Component에 의해 자동으로 Bean이 등록.
클라이언트가 특정 요청을 하게 되면 처리하기 위해서 스프링은 제일 먼저 해당하는 Controller를 찾게 되는데 이때 @Controller라고 명시된 클래스들을 탐색.
그리고 Mapping주소가 일치하는 메소드의 내용을 실행.

@Controller -> view에 객체를 반환하기 위해 사용 but. 데이터를 반환하기 위해서는 @ResponseBody 어노테이션을 사용해주어야 JSON 형태로 데이터 반환 가능.
@RestController -> JSON 형태로 객체 데이터를 반환하는 것이 주용도.
 */
@RequiredArgsConstructor
public class RestaurantApi {

    private final RestaurantService restaurantService; // 요청들에 대한 비즈니스 로직 수행을 위한 restaurantService를 DI.
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
            @RequestBody CreateAndEditRestaurantRequest request // @RequestBody: HTTP 요청의 바디내용을 자바객체로 변환해서 매핑된 메소드 파라미터로 전달.
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
/* @Component 관련 세가지  @Controller, @Service, @Repository의 목적은 빈으로 등록 시켜 IoC(제어의 역전)/DI(의존성 주입) 구현.
-> controller, service, repository의 어노테이션은 모두 컴포넌트 스캔이 가능하게 만드는 수단.
   이 세가지 Annotation은 다음 MVC 패턴 처리 과정과 연관.

   1. 클라이언트가 서버에 페이지를 요청한다.
   2. Dispatcher Servlet은 사용자의 요청에 알맞는 @Controller를 찾는다.
   3. 만약 DB데이터를 이용한 처리 과정이 없다면, 단순히 View를 보여주면 여기서 끝이다.
   4. 만약 DB가 필요하다면 @Controller는 알맞은 @Service로 가서 비지니스 로직을 수행한다.
   5. 수행하는 과정중 DB를 접근하기 위하여 @Repository에 요청하여 DB로 부터 필요한 값을 가져온다.
   6. 순서대로 @Controller까지 결과값을 가지고 return을 하게 되며, Model에 담은 다음에 View를 찾아 보여준다.

   @Controller
   : @Controller라고 명시된 해당 클래스의 Mapping을 스캔.
     + @Controller라고 명시가 되면, 해당 클래스는 @Component에 의해 자동으로 Bean이 등록 됩니다.
       클라이언트가 특정 요청을 하게 되면 처리하기 위해서 스프링은 제일 먼저 해당하는 Controller를 찾게 되는데 이때 @Controller라고 명시된 클래스들을 탐색.
       그리고 난 다음에 Mapping주소가 일치하는 메소드의 내용을 실행.

   @Service
   : @Service라고 명시되는 클래스는  비지니스 로직에 대한 정보를 담고 있음.
     + @Service 라고 명시된 클래스는 비지니스 로직에 대한 정보들이 담겨 있음
       즉 사용자의 요청에 따라서 DB에 접근하여 데이터를 추가, 삭제, 수정, 선택과 같은 요청을 처리할 수 있음
       프로젝트의 규모가 커질수록 Service에 대한 내용이 매우 방대해지니, 처음에 설계할때 비슷한 서비스끼리 잘 나눠서 정리.
       유지보수 및 기능확장 측면에서 매우 유용.

   @Repository
   : @Repository라고 명시되있으면 DB에 접근을 하는 객체.
     + @Repository 어노테이션은 해당 객체는 DB에서 CRUD와 같은 명렁을 하게 되며, DB 접근이 가능한 객체임.
       메소드명으로 DB에서 어떠한 명령을 하는지 나타내고, 해당 명령을 실행하기 위한 데이터를 매개변수로 받을 수 있음.


 */
