package eggis0.baram.domain2.announcement.dto.req;

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