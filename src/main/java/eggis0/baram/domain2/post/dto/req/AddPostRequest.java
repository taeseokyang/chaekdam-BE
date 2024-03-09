package eggis0.baram.domain2.post.dto.req;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class AddPostRequest {
    private String title;
    private String location;
    private String locationDetail;
    private long rentalFee;
    private String security;
//    private LocalDateTime createdAt;
    private LocalDate needAt;
    private LocalDate returnAt;
    private String content;
}
