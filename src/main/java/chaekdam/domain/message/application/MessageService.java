package chaekdam.domain.message.application;

import chaekdam.domain.message.exception.PermissionDeniedException;
import chaekdam.domain.message.repository.MessageRepository;
import chaekdam.domain.user.domain.User;
import chaekdam.domain.user.repository.UserRepository;
import chaekdam.domain.chat.domain.ChatRoom;
import chaekdam.domain.chat.exception.ChatRoomNotFoundException;
import chaekdam.domain.chat.repository.ChatRoomRepository;
import chaekdam.domain.message.domain.Message;
import chaekdam.domain.message.dto.res.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;

    public Message save(String message, String roomId, String userName) {
        User user = userRepository.findByUserId(userName).get();
        ChatRoom chatRoom = chatRoomRepository.findByRoomId(roomId);
        LocalDateTime sentAt = LocalDateTime.now();

        Message saveMessage = Message.builder()
                .message(message)
                .sentAt(sentAt)
                .chatRoom(chatRoom)
                .user(user)
                .build();
        return messageRepository.save(saveMessage);
    }

    public List<MessageResponse> getAllMessageByRoomId(String roomId, Long lastMessageId) {

        if (!chatRoomRepository.existsByRoomId(roomId)) {
            throw new ChatRoomNotFoundException();
        }

        ChatRoom chatRoom = chatRoomRepository.findByRoomId(roomId);
        List<Message> messagePage = messageRepository.findTop20ByChatRoomAndChatIdLessThanOrderByChatIdDesc(chatRoom, lastMessageId);

        List<MessageResponse> messagesDTO = new ArrayList<>();
        for (Message message : messagePage) {
            MessageResponse dto = new MessageResponse(message);
            messagesDTO.add(dto);
        }

        return messagesDTO;
    }
}
