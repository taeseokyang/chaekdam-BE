package eggis0.baram.domain.chat.application;

import eggis0.baram.domain.chat.dto.req.AddChatRoomRequest;
import eggis0.baram.domain.chat.domain.ChatRoom;
import eggis0.baram.domain.chat.dto.res.ChatRoomResponse;
import eggis0.baram.domain.chat.dto.res.ChatRoomsResponse;
import eggis0.baram.domain.chat.repository.ChatRoomRepository;
import eggis0.baram.domain.message.domain.Message;
import eggis0.baram.domain.post.domain.Post;
import eggis0.baram.domain.message.repository.MessageRepository;
import eggis0.baram.domain.post.repository.PostRepository;
import eggis0.baram.domain.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import eggis0.baram.domain.user.domain.User;
import eggis0.baram.domain.user.domain.UserType;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Data
@Service
public class ChatService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final MessageRepository messageRepository;
    private final ObjectMapper mapper;
    public List<ChatRoom> findAllRoom(){
        List<ChatRoom> chatRooms = chatRoomRepository.findAll();
        return chatRooms;
    }

    public List<List<ChatRoomsResponse>> findAllByUser(String userName){
        if (userRepository.existsUserByUserId(userName)){
            User user = userRepository.findByUserId(userName).get();
            List<ChatRoom> chatRoomsForBorrowing = chatRoomRepository.findAllByBorrowerOrderByCreatedAtDesc(user);
            List<ChatRoom> chatRoomsForLending = chatRoomRepository.findAllByLenderOrderByCreatedAtDesc(user);
            List<List<ChatRoomsResponse>> BorrowAndLendDTO = new ArrayList<>();


            String lastMessage;
            LocalDateTime lastMeesageTime;
            List<ChatRoomsResponse> chatRoomsDTO = new ArrayList<>();
            for(ChatRoom chatRoom : chatRoomsForBorrowing){
                Optional<Message> optionalLastMessage = messageRepository.findFirstByChatRoomOrderBySentAtDesc(chatRoom);
                if (optionalLastMessage.isPresent()) {
                    lastMessage = optionalLastMessage.get().getMessage();
                    lastMeesageTime = optionalLastMessage.get().getSentAt();
                } else {
                    lastMessage = "no message";
                    lastMeesageTime = LocalDateTime.now();
                }
                ChatRoomsResponse dto = new ChatRoomsResponse(chatRoom, UserType.BORROWER, lastMessage, lastMeesageTime);
                chatRoomsDTO.add(dto);
            }
            BorrowAndLendDTO.add(chatRoomsDTO);
            chatRoomsDTO = new ArrayList<>();
            for(ChatRoom chatRoom : chatRoomsForLending){
                Optional<Message> optionalLastMessage = messageRepository.findFirstByChatRoomOrderBySentAtDesc(chatRoom);
                if (optionalLastMessage.isPresent()) {
                    lastMessage = optionalLastMessage.get().getMessage();
                    lastMeesageTime = optionalLastMessage.get().getSentAt();
                } else {
                    lastMessage = "no message";
                    lastMeesageTime = LocalDateTime.now();
                }
                ChatRoomsResponse dto = new ChatRoomsResponse(chatRoom, UserType.LENDER, lastMessage, lastMeesageTime);
                chatRoomsDTO.add(dto);
            }
            BorrowAndLendDTO.add(chatRoomsDTO);
            return BorrowAndLendDTO;
        }
        return new ArrayList<>();
    }

    public ChatRoomResponse createRoom(AddChatRoomRequest request) {
        String roomId = UUID.randomUUID().toString();
        User borrower = userRepository.findById(request.getBorrowerId()).get();
        User lender = userRepository.findById(request.getRenderId()).get();
        Post post = postRepository.findById(request.getPostId()).get();
        Optional<ChatRoom> alreadyExistChatRoom = chatRoomRepository.findByBorrowerAndLenderAndPost(borrower,lender,post);
        if (alreadyExistChatRoom.isPresent()){
            return new ChatRoomResponse(alreadyExistChatRoom.get());
        }
        LocalDateTime createdAt = LocalDateTime.now();
        ChatRoom chatRoom = ChatRoom.builder()
                .roomId(roomId)
                .borrower(borrower)
                .lender(lender)
                .post(post)
                .createdAt(createdAt)
                .build();
        return new ChatRoomResponse(chatRoomRepository.save(chatRoom));
    }

    public <T> void sendMessage(WebSocketSession session, T message) {
        try{
            session.sendMessage(new TextMessage(mapper.writeValueAsString(message)));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
