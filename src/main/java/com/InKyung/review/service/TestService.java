package com.InKyung.review.service;

import com.InKyung.review.model.TestEntity;
import com.InKyung.review.repository.TestRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class TestService {
    private final TestRepository testRepository;

    /*
    public TestService(TestRepository testRepository){
        this.testRepository = testRepository;
    } 롬복을 사용하지 않을 시 이와 같이 직접 생성자를 작성해줘야함.
     */

    public void create(String name, Integer age){
        TestEntity testEntity = new TestEntity(name, age);
        testRepository.save(testEntity);
    }

    public void update(Long id, String name, Integer age){
        TestEntity testEntity = testRepository.findById(id).orElseThrow(); // 해당하는  id가 없다면 예외 발생시킴.
        testEntity.changeNameAndAge(name, age);
        testRepository.save(testEntity);
    }

    public void delete(Long id){
        TestEntity testEntity = testRepository.findById(id).get();
        testRepository.delete(testEntity);
    }
    public List<TestEntity> findAllByNameByJPA(String name){
        return testRepository.findAllByName(name);
    } // JPA로 가져오는 구문.

    public List<TestEntity> findAllByNameByQuerydsl(String name){
        return testRepository.findAllByNameByQuerydsl(name);
    } // Querydsl로 가져오는 구문.
}
