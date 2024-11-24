package chaekdam.domain.participant.dto.res;

import chaekdam.domain.chat.domain.ChatRoom;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ChatRoomsResponse {
    private Long id;
    private String roomId;
    private String bookImgPath;
    private String bookName;
    private Long peopleCount;
    private String lastMessage;
    private LocalDateTime lastMessageTime;

    @Builder
    public ChatRoomsResponse(ChatRoom chatRoom, String lastMessage, LocalDateTime lastMessageTime){
        id = chatRoom.getId();
        roomId = chatRoom.getRoomId();
        bookName = chatRoom.getBook().getTitle();
        bookImgPath = chatRoom.getBook().getCoverImgName();
        peopleCount = chatRoom.getPeopleCount();
        this.lastMessage = lastMessage;
        this.lastMessageTime = lastMessageTime;
    }
}
