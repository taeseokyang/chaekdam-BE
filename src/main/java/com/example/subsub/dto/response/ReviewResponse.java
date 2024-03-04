package com.example.subsub.dto.response;

import com.example.subsub.domain.RateType;
import com.example.subsub.domain.Review;
import com.example.subsub.domain.UserType;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

//@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReviewResponse {
    private Long reviewId;
    private UserType writerType;
    private RateType rate;
    private String text;
    private LocalDateTime createdAt;

    public ReviewResponse(Review reviews) {
        reviewId = reviews.getReviewId();
        writerType = reviews.getWriterType();
        rate = reviews.getRate();
        text = reviews.getText();
        createdAt = reviews.getCreatedAt();
    }
}
