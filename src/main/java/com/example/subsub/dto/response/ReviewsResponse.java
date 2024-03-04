package com.example.subsub.dto.response;

import com.example.subsub.domain.RateType;
import com.example.subsub.domain.Review;
import com.example.subsub.domain.UserType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

//@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReviewsResponse {
    private Integer loveCount;
    private Integer goodCount;
    private Integer badCount;

    private List<Review> reviews;

    public ReviewsResponse(Integer loveCount,Integer goodCount,Integer badCount,List<Review> reviews) {
        this.loveCount = loveCount;
        this.goodCount = goodCount;
        this.badCount = badCount;
        this.reviews = reviews;
    }
}
