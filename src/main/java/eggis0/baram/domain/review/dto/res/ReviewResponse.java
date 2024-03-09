package eggis0.baram.domain.review.dto.res;

import eggis0.baram.domain.review.domain.RateType;
import eggis0.baram.domain.review.domain.Review;
import eggis0.baram.domain.user.domain.UserType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

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
