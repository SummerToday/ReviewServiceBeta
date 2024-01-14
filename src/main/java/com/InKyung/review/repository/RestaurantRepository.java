package com.InKyung.review.repository;

import com.InKyung.review.model.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Long> { // <엔티티의 타입클래스, 해당 클래스의 PK 값의 타입>

}

/*
    * JPARepository 사용법

      1. Entity 클래스 정의
         -> JPARepository를 사용하여 액세스할 엔티티 클래스를 정의해야 합니다.

      2. JpaRepository 인터페이스 상속받는 인터페이스 생성
         -> JPARepository 인터페이스를 상속받아, 커스텀합니다.

      3. Spring Bean 등록
         -> JPARepository를 사용하여 데이터 액세스를 수행하는 EntityManager가 필요하므로, JpaRepository를 사용하는 클래스는 빈으로 등록되어야 함.

      4. JPARepository method 사용
         -> CRUD 메서드, List CRUD 메서드, Query Creation 메서드, 영속성 컨텍스트 관련 메서드

 */