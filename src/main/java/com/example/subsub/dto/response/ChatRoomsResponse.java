package com.example.subsub.dto.response;

import com.example.subsub.domain.ChatRoom;
import com.example.subsub.domain.UserType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ChatRoomsResponse {
    private Integer id;
    private String roomId;
    private Integer postId;
    private UserType userType;
    private String borrowerNickname;
    private String lenderNickname;
    private String lastMessage;
    private LocalDateTime lastMessageTime;

    @Builder
    public ChatRoomsResponse(ChatRoom chatRoom,UserType userType, String lastMessage, LocalDateTime lastMessageTime){
        id = chatRoom.getId();
        roomId = chatRoom.getRoomId();
        postId = chatRoom.getPost().getPostId();
        this.userType = userType;
        borrowerNickname = chatRoom.getBorrower().getNickName();
        lenderNickname = chatRoom.getLender().getNickName();
        this.lastMessage = lastMessage;
        this.lastMessageTime = lastMessageTime;
    }
}
