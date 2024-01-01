package com.InKyung.review.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
