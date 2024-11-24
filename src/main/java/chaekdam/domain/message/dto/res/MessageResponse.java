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
    private LocalDateTime sentAt;

    @Builder
    public MessageResponse(Message message) {
        chatId = message.getChatId();
        this.message = message.getMessage();
        sentAt = message.getSentAt();
    }
}
