package com.InKyung.review.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor // 빈 생성자를 생성해주는 어노테이션. bc. Entity는 빈 생성자가 필요.
@Table(name = "restaurant")
@Entity
public class RestaurantEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;
    private ZonedDateTime createdAt;  // Entity의 timestamp 형식은 자바 데이터 타입에서는 로컬 테이터 타입 or ZonedDateTime으로 만들어주면 됨.
    private ZonedDateTime updatedAt;

    public void changeNameAndAddress(String name, String address){
        this.name = name;
        this.address = address;
        this.updatedAt = ZonedDateTime.now();
    }

}
