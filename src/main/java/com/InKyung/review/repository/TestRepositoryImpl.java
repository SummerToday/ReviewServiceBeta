package com.InKyung.review.repository;

import com.InKyung.review.model.QTestEntity;
import com.InKyung.review.model.TestEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class TestRepositoryImpl implements TestRepositoryCustom{
    private final JPAQueryFactory queryFactory; // final 선언하지 않을 시 오류 발생.

    @Override
    public List<TestEntity> findAllByNameByQuerydsl(String name) {
        return queryFactory.selectFrom(QTestEntity.testEntity).fetch();
    } // alt + Ins -> Implement Methods를 통해 구현해야 할 메소드 자동 구현 가능.
}
