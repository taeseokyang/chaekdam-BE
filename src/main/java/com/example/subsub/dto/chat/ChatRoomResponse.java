package com.example.subsub.dto.chat;

import com.example.subsub.domain.ChatRoom;
import com.example.subsub.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ChatRoomResponse {

    private Integer id;
    private String roomId;
    private Long borrowerId;
    private String borrowerName;
    private Long renderId;
    private String renderName;
    private Integer postId;
    private String postName;

    @Builder
    public ChatRoomResponse(ChatRoom chatRoom){
        id = chatRoom.getId();
        roomId = chatRoom.getRoomId();
        borrowerId = chatRoom.getBorrower().getId();
        borrowerName = chatRoom.getBorrower().getUserId();
        renderId = chatRoom.getLender().getId();
        renderName = chatRoom.getLender().getUserId();
        postId = chatRoom.getPost().getPostId();
        postName = chatRoom.getPost().getTitle();
    }
}
