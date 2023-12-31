package com.InKyung.review.api;

import org.springframework.web.bind.annotation.*;

@RestController
public class TestRequestApi {

    //Request Parameter 방식
    @GetMapping("/test/param")
    public String requestParam(
            @RequestParam("name") String name,
            @RequestParam("age") Integer age
            //postman 에서 요청을 보낼 때도 파라미터에 맞는 값을 보내주어야 정상 호출.
    ){
        return "Hello, Request Param, I am "+ name + ", " + age ;
    }

    //Path Variable 방식: path를 변수 처럼 사용하는 방식.
    @GetMapping("/test/path/{name}/{age}") // "~" ~ : path
    //Postman에서 처음 localhost:8080 이후 부분이 path
    public String requestPathVariable(
            @PathVariable("name") String name,
            @PathVariable("age") Integer age
    ){
        return "Hello, Path Variable "+ name + "," + age;
    }

    // Request Body 방식 -> Request body 방식은 PostMapping 이나 PutMapping 에서 많이 사용
    // Request body 방식을 사용하기 위해서는 추가 클래스 + 추가 클래스의 생성자 필요
    @PostMapping("/test/body")
    public String requestBody(
            @RequestBody TestRequestBody request
    ){
        return "Hello, Request Body, I am "+ request.name + ", " + request.age;
    }

    public static class TestRequestBody {
        public TestRequestBody(String name, Integer age) {
            this.name = name;
            this.age = age;
        } // Alt + Insert 단축키 후 생성자에서 초기화 할 변수 선택.

        String name;
        Integer age;
    }
}
