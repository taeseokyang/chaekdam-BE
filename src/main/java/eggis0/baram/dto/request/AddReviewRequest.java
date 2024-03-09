package eggis0.baram.dto.request;

import eggis0.baram.domain.RateType;
import eggis0.baram.domain.UserType;
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
