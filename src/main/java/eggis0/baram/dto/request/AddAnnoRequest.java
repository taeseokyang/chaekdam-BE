package eggis0.baram.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class AddAnnoRequest {
    private String title;
    private String content;
    private LocalDateTime createdAt;
}