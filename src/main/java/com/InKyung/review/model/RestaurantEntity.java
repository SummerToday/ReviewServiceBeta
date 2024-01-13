package com.InKyung.review.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
@Builder // 빌더 패턴 생성 -> 빌더를 통해 객체 생성 가능. bc. 생성자 파라미터가 많을 경우 생성자를 통해 초기화 하는 것보다 빌더를 사용하는 것이 가독성이 좋고 편리. 초기화 순서는 상관x.
@Getter
@AllArgsConstructor
@NoArgsConstructor // 빈 생성자를 생성해주는 어노테이션. bc. JPA Entity는 빈 생성자가 필요.
@Table(name = "restaurant") // @Table은 엔티티와 매핑할 데이터베이스 테이블을 지정. 생략 시, 엔티티 이름을 테이블 이름으로 사용.
@Entity // JPA 엔티티 클래스임을 명시. -> 테이블과 매핑할 클래스
public class RestaurantEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) // 주키(Primary Key)를 자동으로 생성. 데이터베이스의 AUTO_INCREMENT 기능을 통해 데이터베이스가 기본 키를 자동 생성
    private Long id; // 기본키 정의

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

/* 노션 참고 자료: https://busy-jellyfish-dcb.notion.site/2aa05ccf0e76475381bc85f36d5f7f41

   * 기본키 매핑
     1)직접 할당 : 기본 키를 애플리케이션에서 직접 엔티티클래스의 @Id 필드에 set해준다.
     2)자동 생성 : 대리 키 사용 방식
     - IDENTITY : 기본 키 생성을 데이터베이스에 위임한다.(ex MySQL - AUTO INCREMENT...)
     - SEQUENCE : 데이터베이스 시퀀스를 사용해서 기본 키를 할당한다.(ex Oracle sequence...)
     - TABLE : 키 생성 테이블을 사용한다.(ex 시퀀스용 테이블을 생성해서 테이블의 기본키를 저장하고 관리한다.)

     자동 생성 전략이 이렇게 다양한 이유는 데이터베이스 벤더마다 지원하는 방식이 다르기 때문.
     위 중에서 IDENTITY와 SEQUENCE는 데이터베이스 벤더에 의존적이다. 하지만 TABLE 전략은 키 생성용 테이블을 하나 만들어두고 마치 시퀀스처럼
     사용하는 방법이기에 벤더에 의존하지 않는다.(하지만 각각 장단점이 존재함)


   * @Entity 적용 시에는, 클래스가 아래 주의사항을 따라야함.
     - 기본생성자 필수
     - final, enum, interface, inner 클래스에는 사용 불가
     - 테이블에 저장할 필드는 final 키워드 사용 불가
     + 키 생성 전략을 사용하려면 persistence.xml 혹은 application.properties에 추가 설정 필요.


 */
