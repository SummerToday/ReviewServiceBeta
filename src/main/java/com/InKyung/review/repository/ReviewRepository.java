package com.InKyung.review.repository;

import com.InKyung.review.model.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long>, ReviewRepositoryCustom { // 기본적인 JpaRepository 함수와 더불어 Impl 에 정의된 Querydsl 함수들을 모두 사용 가능. + 인터페이스끼리는 다중 상속 가능.

}
