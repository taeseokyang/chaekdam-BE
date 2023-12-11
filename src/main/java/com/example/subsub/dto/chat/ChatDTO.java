package com.example.subsub.dto.chat;

import com.example.subsub.domain.MessageType;
import com.example.subsub.domain.UserType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChatDTO {
    // 메시지  타입 : 입장, 채팅
    private UserType userType;
    private MessageType type; // 메시지 타입
    private String roomId; // 방 번호
    private String sender; // 채팅을 보낸 사람
    private String message; // 메시지
    private String time; // 채팅 발송 시간간
}