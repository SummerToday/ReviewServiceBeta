package com.InKyung.review.service.dto;

import com.InKyung.review.model.ReviewEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
@Getter
@AllArgsConstructor
@Builder
public class ReviewDto {
    private Double avgScore;
    private List<ReviewEntity> reviews;
    private ReviewDtoPage page;

    @AllArgsConstructor
    @Builder
    @Getter
    public static class ReviewDtoPage{
        private Integer offset;
        private Integer limit;
    }
}
