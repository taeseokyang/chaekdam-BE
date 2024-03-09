package eggis0.baram.domain.review.dto.req;

import eggis0.baram.domain.review.domain.RateType;
import eggis0.baram.domain.user.domain.UserType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddReviewRequest {
    private Integer postId;
    private UserType writerType;
    private String recipientId;
    private RateType rate;
    private String text;
}
