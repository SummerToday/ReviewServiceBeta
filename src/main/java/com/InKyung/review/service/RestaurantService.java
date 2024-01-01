package com.InKyung.review.service;

import com.InKyung.review.api.request.CreateAndEditRestaurantRequest;
import com.InKyung.review.model.MenuEntity;
import com.InKyung.review.model.RestaurantEntity;
import com.InKyung.review.repository.MenuRepository;
import com.InKyung.review.repository.RestaurantRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;
    @Transactional
    public RestaurantEntity createRestaurant(
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
    public void editRestaurant(
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
    public void deleteRestaurant(Long restaurantId){
        RestaurantEntity restaurant = restaurantRepository.findById(restaurantId).orElseThrow(); // 예외 처리도 같이 구현.
        restaurantRepository.delete(restaurant);

        List<MenuEntity> menus = menuRepository.findAllByRestaurantId(restaurantId);
        menuRepository.deleteAll(menus);
    }
}
