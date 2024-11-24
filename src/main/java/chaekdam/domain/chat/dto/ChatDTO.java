package chaekdam.domain.chat.dto;

import chaekdam.domain.message.domain.MessageType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ChatDTO {
    // 메시지  타입 : 입장, 채팅
    private MessageType type;
    private String roomId;
    private String nickname;
    private String userImgPath;
    private String userId;
    private String message;
    private LocalDateTime sentAt;
}