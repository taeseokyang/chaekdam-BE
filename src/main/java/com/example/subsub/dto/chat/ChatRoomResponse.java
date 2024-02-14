package com.example.subsub.dto.chat;

import com.example.subsub.domain.ChatRoom;
import com.example.subsub.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ChatRoomResponse {

    private Integer id;
    private String roomId;
    private String borrowerId;
    private String borrowerNickname;
    private String renderId;
    private String renderNickname;
    private Integer postId;
    private String postName;

    @Builder
    public ChatRoomResponse(ChatRoom chatRoom){
        id = chatRoom.getId();
        roomId = chatRoom.getRoomId();
        borrowerId = chatRoom.getBorrower().getId();
        borrowerNickname = chatRoom.getBorrower().getNickName();
        renderId = chatRoom.getLender().getId();
        renderNickname = chatRoom.getLender().getNickName();
        postId = chatRoom.getPost().getPostId();
        postName = chatRoom.getPost().getTitle();
    }
}
