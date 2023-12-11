package com.example.subsub.dto.chat;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class AddChatRoomRequest {
    private String roomId;
    private Long borrowerId;
    private Long renderId;
    private Integer postId;
}
