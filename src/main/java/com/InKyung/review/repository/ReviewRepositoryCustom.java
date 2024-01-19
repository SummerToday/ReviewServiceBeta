package com.InKyung.review.repository;

import com.InKyung.review.model.ReviewEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;


public interface ReviewRepositoryCustom { // 해당 인터페이스에서 선언한 함수는 해당 Custom 인터페이스를 구현하는 Impl 클래스에서 정의.

    Double getAvgScoreByRestaurantId(Long restaurantId);
    Slice<ReviewEntity> findSliceByRestaruantId(Long restaurantId, Pageable page); // query method에 Pageable을 인자로 주면 return으로 Slice, Page, List 등의 타입을 받을 수 있음.
}
// 이런 구조를 만들어 놓으면 기본적인 작업과 Custom한 작업을 하나의 repository로 관리 할 수 있기 때문에 코드가 깔끔해지는 장점이 있음.
