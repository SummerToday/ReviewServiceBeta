package com.InKyung.review.api;

import com.InKyung.review.service.TestService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
public class TestEntityApi {
    private final TestService testService;  // 생성자 필요 -> @AllArgsConstructor 사용

    @PostMapping("/test/entity/create")
    public void createTestEntity(
            @RequestBody CreateTestEntityRequest request
    ){
        testService.create(request.getName(),request.getAge());
    }
    @DeleteMapping("/test/entity/{id}")
    public void deleteTestEnytity(
            @PathVariable Long id
    ){
        testService.delete(id);
    }

    @PutMapping("/test/entity/{id}")
    public void putTestEntity(
            @PathVariable Long id,
            @RequestBody CreateTestEntityRequest request
    ){
        testService.update(id, request.getName(), request.getAge());
    }

    @AllArgsConstructor
    @Getter
    public static class CreateTestEntityRequest {
        private final String name;
        private final Integer age;
    }
}
// postman에서 200 OK 반응이 올 시 정상적으로 처리 되었다는 의미.
