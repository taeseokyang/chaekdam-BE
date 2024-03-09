package eggis0.baram.domain2.message.application;

import eggis0.baram.domain2.message.domain.Message;
import eggis0.baram.domain2.message.repository.MessageRepository;
import eggis0.baram.domain2.user.domain.UserType;
import eggis0.baram.domain2.chat.repository.ChatRoomRepository;
import eggis0.baram.domain2.user.repository.UserRepository;
import eggis0.baram.domain2.chat.domain.ChatRoom;
import eggis0.baram.domain2.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;

    public Message save(String message, String roomId, String userName, UserType userType) {
        User user = userRepository.findByUserId(userName).get();
        ChatRoom chatRoom = chatRoomRepository.findByRoomId(roomId);
        LocalDateTime sentAt = LocalDateTime.now();

        Message saveMessage = Message.builder()
                .message(message)
                .sentAt(sentAt)
                .chatRoom(chatRoom)
                .user(user)
                .userType(userType)
                .build();
        return messageRepository.save(saveMessage);
    }

    public List<Message> getAllMessageByRoomId(String roomId, String userName) {
        if (chatRoomRepository.existsByRoomId(roomId)){
            ChatRoom chatRoom = chatRoomRepository.findByRoomId(roomId);
            if (chatRoom.getBorrower().getUserId().equals(userName) || chatRoom.getLender().getUserId().equals(userName))
                return messageRepository.findAllByChatRoomOrderBySentAtAsc(chatRoom);
        }
        return Collections.emptyList();
    }
}
