package com.InKyung.review.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestLombokApi {

    @GetMapping("/test/lombok")
    public TestLombokResponseBody testLombok(){
        return new TestLombokResponseBody( "Inkyung", 25);
    }

    @Getter //어노테이션을 통해 getter 메소드 자동 생성.
    @AllArgsConstructor //생성자도 마찬가지.
    public static class TestLombokResponseBody{
        String name;
        Integer age;
    }

}
    /*
    - 롬복 Lombok ?
    => Java의 라이브러리로 반복되는 메서드를 Anntation을 사용해 자동으로 작성해주는 라이브러리.
       Lombok을 사용시 어노테이션을 이용해 자동으로 Getter, Setter, 생성자 등의 코드를 작성하여 번거로운 과정을 제거.

    - gradle ?
    Gradle은 다양한 언어 및 플랫폼에 대한 빌드 자동화를 지원하는 도구로, Java, Scala, Android, Kotlin, C/C++, Groovy 등을 포함한
    여러 언어와 플랫폼에 대한 유연한 모델을 제공.
    Eclipse, IntelliJ, Jenkins와 밀접하게 통합되어 개발 생명주기 전반에 걸쳐 코드 컴파일, 패키징, 웹 사이트 게시물 등을 지원하며,
    소프트웨어 빌드, 테스트, 배포를 효과적으로 관리할 수 있는 도구.
     */

