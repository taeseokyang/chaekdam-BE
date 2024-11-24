package chaekdam.domain.message.dto.res;

import chaekdam.domain.message.domain.Message;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MessageResponse {

    private Long chatId;
    private String message;
    private String userId;
    private String nickname;
    private String userImgPath;
    private LocalDateTime sentAt;

    @Builder
    public MessageResponse(Message message) {
        chatId = message.getChatId();
        userId = message.getUser().getUserId();
        nickname = message.getUser().getNickName();
        userImgPath = message.getUser().getImgPath();
        this.message = message.getMessage();
        sentAt = message.getSentAt();
    }
}
