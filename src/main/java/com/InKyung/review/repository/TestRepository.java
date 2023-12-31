package com.InKyung.review.repository;

import com.InKyung.review.model.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestRepository extends JpaRepository<TestEntity, Long>, TestRepositoryCustom {
    //id이 데이터 형식 Long을 매개변수로 입력.

    public List<TestEntity> findAllByName(String name);

}
