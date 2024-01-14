package com.InKyung.review.repository;

import com.InKyung.review.model.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<MenuEntity, Long> {
    public List<MenuEntity> findAllByRestaurantId(Long restaurantId); // JPARepository List CRUD Method, 주어진 ID에 해당하는 엔티티를 조회.
}
