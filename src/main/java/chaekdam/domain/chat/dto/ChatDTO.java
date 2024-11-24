package chaekdam.domain.chat.dto;

import chaekdam.domain.message.domain.MessageType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ChatDTO {
    // 메시지  타입 : 입장, 채팅
    private MessageType type; // 메시지 타입
    private String roomId; // 방 번호
    private String nickname; // 채팅을 보낸 사람
    private String userImgPath; // 채팅을 보낸 사람
    private String userId; // 채팅을 보낸 사람
    private String message; // 메시지
    private LocalDateTime sentAt; // 채팅 발송 시간간
}