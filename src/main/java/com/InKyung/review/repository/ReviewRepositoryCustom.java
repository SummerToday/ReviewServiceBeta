package com.InKyung.review.repository;

import com.InKyung.review.model.ReviewEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;


public interface ReviewRepositoryCustom { // 해당 인터페이스에서 선언한 함수는 해당 Custom 인터페이스를 구현하는 Impl 클래스에서 정의.

    Double getAvgScoreByRestaurantId(Long restaurantId);
    Slice<ReviewEntity> findSliceByRestaruantId(Long restaurantId, Pageable page); // query method에 Pageable을 인자로 주면 return으로 Slice, Page, List 등의 타입을 받을 수 있음.
}
// 이런 구조를 만들어 놓으면 기본적인 작업과 Custom한 작업을 하나의 repository로 관리 할 수 있기 때문에 코드가 깔끔해지는 장점이 있음.
/*
   * Page vs Slice
      Page는 Slice를 상속. 따라서 Slice가 가진 모든 메서드를 Page도 사용할 수 있음. 다만 Page가 다른 점은 조회 쿼리 이후 전체 데이터 개수를 조회하는 쿼리가 한 번 더 실행된다는 것.
      Page가 추가적으로 구현하고 있는 메서드 두 가지 -> getTotalElements() - 전체 데이터 개수를 반환, getTotalPages() - 전체 페이지 수를 반환.
      Page의 경우 전체 데이터 개수를 조회하는 쿼리가 추가적으로 실행되므로 Slice와 다르게 전체 데이터 개수나 전체 페이지 수까지 확인할 수 있음

      => Slice는 전체 데이터 개수를 조회하지 않고 이전 or 다음 Slice가 존재하는지만 확인할 수 있음. 따라서 Slice는 무한 스크롤 등을 구현하는 경우 유용. Page에 비해 쿼리가 하나 덜 날아가므로 데이터 양이 많을수록 Slice를 사용하는 것이 성능상 유리.
         Page는 전체 데이터 개수를 조회하는 쿼리를 한 번 더 실행. 따라서 전체 페이지 개수나 데이터 개수가 필요한 경우 유용.

 */

