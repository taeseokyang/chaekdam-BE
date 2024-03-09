package eggis0.baram.domain2.review.dto.req;

import eggis0.baram.domain2.review.domain.RateType;
import eggis0.baram.domain2.user.domain.UserType;
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
