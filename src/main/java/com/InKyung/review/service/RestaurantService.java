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
    @Transactional // 2개 이상의 쿼리를 하나의 처리 단위로 묶어 DB로 전송하고, 메소드 내에서 수행되는 모든 데이터베이스 작업은 하나의 트랜잭션으로 묶임.
                   // 이 트랜잭션은 메소드 내에서 예외가 발생하지 않으면 커밋되고, 예외가 발생하면 롤백됨.
    public void createRestaurant( // 맛집 정보 생성 로직.
            CreateAndEditRestaurantRequest request
    ){
        RestaurantEntity restaurant = RestaurantEntity.builder()  // @build를 통해 생성된 빌더클래스 생성자.
                .name(request.getName())
                .address(request.getAddress())
                .createdAt(ZonedDateTime.now())
                .updatedAt(ZonedDateTime.now())
                .build(); // bc.id 속성은 생성할 필요가 없기 때문에 생성x. build()가 객체를 생성해 봔환.

        restaurantRepository.save(restaurant); // restaurant 엔티티를 DB에 저장. DB 없던 데이터를 저장하니 INSERT 실행.

        request.getMenus().forEach((menu) -> { // 가져온 메뉴 리스트에 람다 표현식을 이용하여 해당 메뉴 엔티티의 객체를 생성 삽입하여 저장하는 하는 로직 반복. menu는 임의의 매개변수.
            MenuEntity menuEntity = MenuEntity.builder()
                    .restaurantId(restaurant.getId())
                    .name(menu.getName())
                    .price(menu.getPrice())
                    .createdAt(ZonedDateTime.now())
                    .updatedAt(ZonedDateTime.now())
                    .build();

            menuRepository.save(menuEntity);  // menu 엔티티를 DB에 저장. DB 없던 데이터를 저장하니 INSERT 실행.

        });

        //return restaurant;

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
        RestaurantEntity restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new RuntimeException("없는 레스토랑입니다.")); // restaurantId를 사용하여 해당 엔티티를 찾고 없으면 예외 발생.
        restaurant.changeNameAndAddress(request.getName(), request.getAddress()); // 주어진 맛집 이름, 위치로 정보 수정.
        restaurantRepository.save(restaurant); //수정된 레스토랑 엔티티 저장.

        List<MenuEntity> menus = menuRepository.findAllByRestaurantId(restaurantId);
        menuRepository.deleteAll(menus); // 찾아낸 메뉴 엔티티들을 데이터베이스에서 삭제.

        request.getMenus().forEach((menu) -> {
            MenuEntity menuEntity = MenuEntity.builder()
                    .restaurantId(restaurantId)
                    .name(menu.getName())
                    .price(menu.getPrice())
                    .createdAt(ZonedDateTime.now())
                    .updatedAt(ZonedDateTime.now())
                    .build(); // 새로 만든 메뉴 엔티티 객체 생성.

            menuRepository.save(menuEntity); // menu 엔티티를 DB에 저장. DB 없던 데이터를 저장하니 INSERT 실행.
        });
    }

    @Transactional
    public void deleteRestaurant(Long restaurantId){ // 맛집 정보 삭제 로직.
        RestaurantEntity restaurant = restaurantRepository.findById(restaurantId).orElseThrow(); // 예외 처리도 같이 구현.
        restaurantRepository.delete(restaurant); // 해당 맛집 엔티티를 삭제.

        List<MenuEntity> menus = menuRepository.findAllByRestaurantId(restaurantId); // 해당 맛집과 관련된 메뉴 리스트 찾기.
        menuRepository.deleteAll(menus); // 메뉴 삭제.
    }

    @Transactional(readOnly = true) // 읽기 전용 트랜잭션으로 실행.
    public List<RestaurantView> getAllRestaurants(){ // 맛집 리스트 가져오기 로직.
        List<RestaurantEntity> restaurants = restaurantRepository.findAll(); // 데이터 베이스에서 모든 맛집들을 리스트로 조회.

        return restaurants.stream().map((restaurant) -> RestaurantView.builder() // Stream API 사용
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

 * save() 메소드가 호출될 때 save() 메소드는 2가지 방식으로 작동.
    - DB에서 가져오지 않은 새로운 객체를 저장하는 경우
        insert SQL이 나오며 새로운 데이터가 저장되게 됩니다!
    - DB에 이미 저장되어 있는 객체를 저장하는 경우
        update set 필드=변경되는값, ... where id = ? 라는 update SQL이 나가며 기존의 데이터가 업데이트 됨.

 * Stream API의 특징
   - 원본의 데이터를 변경 x.
     -> Stream API는 원본의 데이터를 조회하여 원본의 데이터가 아닌 별도의 요소들로 Stream을 생성.
        때문에 원본의 데이터로부터 읽기만 할 뿐이며, 정렬이나 필터링 등의 작업은 별도의 Stream 요소들에서 처리.

   - 일회용 -> 재사용 불가.
     -> Stream API는 일회용이기 때문에 한번 사용이 끝나면 재사용이 불가능하다. Stream이 또 필요한 경우에는 Stream을 다시 생성해주어야 함.
        만약 닫힌 Stream을 다시 사용한다면 IllegalStateException이 발생.

   - 내부 반복으로 작업을 처리. -> 코드가 간결해짐.
     -> Stream을 이용하면 코드가 간결해지는 이유 중 하나는 '내부 반복' 때문.
        기존에는 반복문을 사용하기 위해서 for이나 while 등과 같은 문법을 사용해야 했지만,
        stream에서는 그러한 반복 문법을 메소드 내부에 숨기고 있기 때문에, 보다 간결한 코드의 작성이 가능.
        ex. nameStream.forEach(System.out::println);

  * Stream API의 3가지 단계
    1. 생성
       Stream 객체를 생성하는 단계 Stream은 재사용이 불가능하므로, 닫히면 다시 생성해주어야함.
       Stream 연산을 하기 위해서는 먼저 Stream 객체를 생성해주어야함.
       배열, 컬렉션, 임의의 수, 파일 등 거의 모든 것을 가지고 스트림을 생성 가능. 주의할 점은 연산이 끝나면 Stream이 닫히기 때문에,
       Stream이 닫혔을 경우 다시 Stream을 생성해야함.

    2. 가공
       원본의 데이터를 별도의 데이터로 가공하기 위한 중간 연산. 연산 결과를 Stream으로 다시 반환하기 때문에 연속해서 중간 연산을 이어갈 수 있음.
       가공하기 단계는 원본의 데이터를 별도의 데이터로 가공하기 위한 중간 연산의 단계.
       어떤 객체의 Stream을 원하는 형태로 처리할 수 있으며, 중간 연산의 반환값은 Stream이기 때문에 필요한 만큼 중간 연산을 연결하여 사용할 수 있음.

    3. 결과 만들기
       가공된 데이터로부터 원하는 결과를 만들기 위한 최종 연산
       Stream의 요소들을 소모하면서 연산이 수행되기 때문에 1번만 처리 가능.

    ex. List<String> myList = Arrays.asList("a1", "a2", "b1", "c2", "c1");

        myList
        .stream()							// 생성하기
        .filter(s -> s.startsWith("c"))     // 가공하기
        .map(String::toUpperCase)			// 가공하기
        .sorted()							// 가공하기
        .count();							// 결과만들기

 */