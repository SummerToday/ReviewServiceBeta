package com.InKyung.review.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestResponseApi {

    @GetMapping("/test/response/string")
    public String stringResponse(){
        return "This is String";
    }

    @GetMapping("/test/response/json")
    public TestResponseBody jsonResponse(){
        return new TestResponseBody("InKyung",25); //데이터를 객체로 만들어서 리턴.
    }

    public static class TestResponseBody{ // getter와 생성자가 보통 같이 존재
        String name;
        Integer age;

        public String getName() {
            return name;
        }

        public Integer getAge() {
            return age;
        }

        public TestResponseBody(String name, Integer age) {
            this.name = name;
            this.age = age;
        }
    }

}

