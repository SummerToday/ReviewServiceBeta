package com.InKyung.review.api;

import org.springframework.web.bind.annotation.*;

import javax.annotation.processing.Generated;

@RestController
public class TestApi {
    @GetMapping("/hello/world")
    public String helloWorld(){
      return "[Get] Hello, world!";
    }
    @PostMapping("/hello/world")
    public String postHelloWorld(){
        return "[Post] Hello, world";
    }
    @PutMapping("/hello/world")
    public String dputHelloWorld(){
        return "[Put] Hello, world";
    }

    @DeleteMapping("/hello/world")
    public String deleteHelloWorld(){
        return "[Delete] Hello, world";
    }
}
