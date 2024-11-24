package chaekdam.domain.participant.dto.res;

import chaekdam.domain.chat.domain.ChatRoom;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ChatRoomResponse {

    private Long id;
    private String roomId;

    @Builder
    public ChatRoomResponse(ChatRoom chatRoom){
        id = chatRoom.getId();
        roomId = chatRoom.getRoomId();
    }
}
