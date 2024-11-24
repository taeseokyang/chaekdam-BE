package chaekdam.domain.chat.application;

import chaekdam.domain.book.domain.Book;
import chaekdam.domain.book.exception.BookNotFoundException;
import chaekdam.domain.book.repository.BookRepository;
import chaekdam.domain.chat.domain.ChatRoom;
import chaekdam.domain.chat.dto.res.ChatRoomResponse;
import chaekdam.domain.chat.dto.res.ChatRoomsResponse;
import chaekdam.domain.chat.exception.FailSendMessageException;
import chaekdam.domain.chat.repository.ChatRoomRepository;
import chaekdam.domain.message.repository.MessageRepository;
import chaekdam.domain.participant.domain.Participant;
import chaekdam.domain.participant.repository.ParticipantRepository;
import chaekdam.domain.user.domain.User;
import chaekdam.domain.user.exception.UserNotFountException;
import chaekdam.domain.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import chaekdam.domain.message.domain.Message;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Data
@Service
public class ChatService {
    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final MessageRepository messageRepository;
    private final BookRepository bookRepository;
    private final ParticipantRepository participantRepository;
    private final ObjectMapper mapper;
    private static final String NO_MESSAGE = "no message";

    public ChatRoom save(String isbn) {
        String roomId = UUID.randomUUID().toString();
        Book book = bookRepository.findByIsbn(isbn).orElseThrow(BookNotFoundException::new);
        Optional<ChatRoom> alreadyExistChatRoom = chatRoomRepository.findByBook(book);
        if (alreadyExistChatRoom.isPresent()) {
            return alreadyExistChatRoom.get();
        }
        ChatRoom chatRoom = ChatRoom.builder()
                .roomId(roomId)
                .book(book)
                .peopleCount(0L)
                .createdAt(LocalDateTime.now())
                .build();
        return chatRoomRepository.save(chatRoom);
    }
    public void increasePeopleCount(ChatRoom chatRoom) {
        chatRoom.setPeopleCount(chatRoom.getPeopleCount()+1);
        chatRoomRepository.save(chatRoom);
    }


    public List<ChatRoomsResponse> getAllByUser(String userName) {
        User user = userRepository.findByUserId(userName).orElseThrow(UserNotFountException::new);
        List<Participant> participantList = participantRepository.findAllByUser(user);
        List<ChatRoomsResponse> chatRoomDTOs = new ArrayList<>();
        for (Participant participant : participantList) {
            Optional<Message> optionalLastMessage = messageRepository.findFirstByChatRoomOrderBySentAtDesc(participant.getChatRoom());
            String lastMessage = NO_MESSAGE;
            LocalDateTime lastMessageTime = LocalDateTime.now();
            if (optionalLastMessage.isPresent()) {
                lastMessage = optionalLastMessage.get().getMessage();
                lastMessageTime = optionalLastMessage.get().getSentAt();
            }
            chatRoomDTOs.add(new ChatRoomsResponse(participant.getChatRoom(), lastMessage, lastMessageTime));
        }
        return chatRoomDTOs;
    }

    public <T> void sendMessage(WebSocketSession session, T message) {
        try {
            if (session != null) {
                session.sendMessage(new TextMessage(mapper.writeValueAsString(message)));
            }
        } catch (IOException e) {
            throw new FailSendMessageException();
        }
    }

}
