package com.InKyung.review.service;

import com.InKyung.review.api.request.CreateAndEditRestaurantRequest;
import com.InKyung.review.api.response.RestaurantDetailView;
import com.InKyung.review.api.response.RestaurantView;
import com.InKyung.review.model.MenuEntity;
import com.InKyung.review.model.QRestaurantEntity;
import com.InKyung.review.model.RestaurantEntity;
import com.InKyung.review.repository.MenuRepository;
import com.InKyung.review.repository.RestaurantRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;



@RequiredArgsConstructor // final로 선언된 모든 필드를 인자 값으로 하는 생성자를 생성. (의존성 주입(DI) - 생성자 주입 방식.
                         // bc. 생성자가 단 한개만 선언이 되어있으면 @Autowired 어노테이션을 생략 가능. 원래는  @Autowired 어노테이션과 생성자를 사용해서
                         //     의존성을 주입해주는 방법이 일반적 but. 이 방식이 더 좋은 방식.)
@Service // 비즈니스 로직 구현.
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;
    @Transactional // 2개 이상의 쿼리를 하나의 처리 단위로 묶어 DB로 전송하고, 이 과정에서 에러가 발생할 경우 자동으로 모든 과정을 되돌려 놓아줌.
                   //클래스에 @Transactional을 붙여주면 메소드까지 모두 적용. 메소드 단위별로 적용도 가능.
    public RestaurantEntity createRestaurant( // 맛집 정보 생성 로직.
            CreateAndEditRestaurantRequest request
    ){
        RestaurantEntity restaurant = RestaurantEntity.builder()
                .name(request.getName())
                .address(request.getAddress())
                .createdAt(ZonedDateTime.now())
                .updatedAt(ZonedDateTime.now())
                .build(); // bc.id 속성은 생성할 필요가 없기 때문에 전체 객체 생성 안하고 build를 통해 각각 생성.

        restaurantRepository.save(restaurant);

        request.getMenus().forEach((menu) -> {
            MenuEntity menuEntity = MenuEntity.builder()
                    .restaurantId(restaurant.getId())
                    .name(menu.getName())
                    .price(menu.getPrice())
                    .createdAt(ZonedDateTime.now())
                    .updatedAt(ZonedDateTime.now())
                    .build();

            menuRepository.save(menuEntity);

        });

        return restaurant;

        /*
         restaurantRepository.save(restaurant);는 restaurant라는 객체를 데이터베이스에 저장하거나
         이미 존재하는 경우에는 업데이트하는 역할을 함. 이 코드를 실행하면 데이터베이스에 해당 레스토랑 정보가 저장되거나 갱신됨.
         so-> id 값이 채워짐.
         */
    }
    @Transactional
    public void editRestaurant( // 맛집 정보 수정 로직.
            Long restaurantId,
            CreateAndEditRestaurantRequest request
    ){
        RestaurantEntity restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new RuntimeException("없는 레스토랑입니다."));
        restaurant.changeNameAndAddress(request.getName(), request.getAddress());
        restaurantRepository.save(restaurant);

        List<MenuEntity> menus = menuRepository.findAllByRestaurantId(restaurantId);
        menuRepository.deleteAll(menus);

        request.getMenus().forEach((menu) -> {
            MenuEntity menuEntity = MenuEntity.builder()
                    .restaurantId(restaurantId)
                    .name(menu.getName())
                    .price(menu.getPrice())
                    .createdAt(ZonedDateTime.now())
                    .updatedAt(ZonedDateTime.now())
                    .build();

            menuRepository.save(menuEntity);
        });
    }

    @Transactional
    public void deleteRestaurant(Long restaurantId){ // 맛집 정보 삭제 로직.
        RestaurantEntity restaurant = restaurantRepository.findById(restaurantId).orElseThrow(); // 예외 처리도 같이 구현.
        restaurantRepository.delete(restaurant);

        List<MenuEntity> menus = menuRepository.findAllByRestaurantId(restaurantId);
        menuRepository.deleteAll(menus);
    }

    @Transactional(readOnly = true)
    public List<RestaurantView> getAllRestaurants(){ // 맛집 리스트 가져오기 로직.
        List<RestaurantEntity> restaurants = restaurantRepository.findAll();

        return restaurants.stream().map((restaurant) -> RestaurantView.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .address(restaurant.getAddress())
                .createdAt(restaurant.getCreatedAt())
                .updatedAt(restaurant.getUpdatedAt())
                .build()
        ).toList();

    } // 수정, 생성을 안하는 읽기만 하는 메소드는  @Transactional을 굳이 붙여주지 않아도 됨.
      // but.  @Transactional(readOnly = true)를 사용 -> 조회 속도 개선 효과도 존재.

    @Transactional(readOnly = true)
    public RestaurantDetailView getRestaurantDetail(Long restaurantId){ // 맛집 정보 가져오기 로직.
        RestaurantEntity restaurant = restaurantRepository.findById(restaurantId).orElseThrow();
        List<MenuEntity> menus = menuRepository.findAllByRestaurantId(restaurantId);

        return RestaurantDetailView.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .address(restaurant.getAddress())
                .createdAt(restaurant.getCreatedAt())
                .updatedAt(restaurant.getUpdatedAt())
                .menus(
                        menus.stream().map((menu) -> RestaurantDetailView.Menu.builder()
                                .id(menu.getId())
                                .name(menu.getName())
                                .price(menu.getPrice())
                                .createdAt(menu.getCreatedAt())
                                .updatedAt(menu.getUpdatedAt())
                                .build()
                        ).toList()
                )
                .build();

    }
}

/*
람다 표현식 ex. (prameters) -> expression
=> 람다 표현식은 메서드로 전달할 수 있는 익명 함수를 단순화한 것.
   람다 표현식에는 이름은 없지만, 파라미터 리스트, 바디, 반환 형식, 발생할 수 있는 예외 리스트는 가질 수 있음.

cf) private final 선언
   -> 직접적으로 값을 참조할 수 없지만 생성자를 통해 값을 참조
      변수를 사용하면 재할당 하지 못하며, 해당 필드, 메서드 별로 호출할 때마다 새로이 값이 할당(인스턴스화)한다.
      객체 생성 시 private final 변수는 초기화가 가능

    private static final 선언
   -> 생성자를 통해 값을 참조할 수 없다.
      private static final 변수는 무조건 초기화 되어있어야 한다.
      즉, 절대 해당 값을 바꾸지 않을 때 사용.
      변수를 사용하면 재할당 하지 못하며, 메모리에 한번 올라가면 같은 값을 클래스 내부의 전체 필드, 메서드에서 공유한다.

    ** 스프링 핵심 4가지 개념 **
    IOC(제어의 역전): 객체의 생성과 관리를 개발자가 하는 것이 아니라 프레임워크가 대신 하는 것.
    DI(의존성 주입): 외부에서 객체를 주입 받아 사용하는 것.
    AOP(관점 지향 프로그래밍): 프로그래밍을 할 때 핵심 관점과 부가 관점을 나누어서 개발하는 것.
    PSA(이식 가능한 서비스 추상화): 어느기술을 사용하던 일관된 방식으로 처리하도록 하는 것.

ex.
@RestController
@RequiredArgsConstructor
@RequestMapping("/example")
public class RequiredArgsConstructorControllerExample {

  private final FirstService firstService;
  private final SecondService secondService;
  private final ThirdService thirdService;

  ...
}
=>
@RestController
@RequestMapping("/example")
public class RequiredArgsConstructorControllerExample {

  private final FirstService firstService;
  private final SecondService secondService;
  private final ThirdService thirdService;

  @Autowired
  public RequiredArgsConstructorControllerExample(FirstService firstService, SecondService secondService, ThirdService thirdService) {
    this.firstRepository = firstRepository;
    this.secondRepository = secondRepository;
    this.thirdRepository = thirdRepository;
  }
}
위 두가지 코드는 같은 코드.

 */