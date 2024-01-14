package com.InKyung.review.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
@Getter
@Builder
@AllArgsConstructor // 클래스의 모든 필드 값을 파라미터로 받는 생성자를 자동으로 생성
@NoArgsConstructor //  파라미터가 없는 디폴트 생성자를 자동으로 생성.
@Entity
@Table(name = "menu")
public class MenuEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long restaurantId;
    private String name;
    private Integer price;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
}
/*
@Builder를 사용한 클래스에서 롬복은 해당 클래스에 대한 빌더 클래스를 자동으로 생성.
이 빌더 클래스를 사용하면 객체를 생성할 때 각 필드에 대한 메서드 체이닝을 통해 값을 설정하거나
선택적으로 값을 빌더 패턴을 통해 지정 가능.
 */
