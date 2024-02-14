package com.example.subsub.dto.chat;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class AddChatRoomRequest {
    private String roomId;
    private String borrowerId;
    private String renderId;
    private Integer postId;
}
