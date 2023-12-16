package com.example.subsub.service;

import com.example.subsub.domain.*;
import com.example.subsub.dto.chat.AddChatRoomRequest;
import com.example.subsub.dto.chat.ChatRoomResponse;
import com.example.subsub.dto.response.ChatRoomsResponse;
import com.example.subsub.repository.ChatRoomRepository;
import com.example.subsub.repository.MessageRepository;
import com.example.subsub.repository.PostRepository;
import com.example.subsub.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    public List<ChatRoomsResponse> findAllByUser(String userName){
        User user = userRepository.findByUserId(userName).get();
        List<ChatRoom> chatRoomsForBorrowing = chatRoomRepository.findAllByBorrower(user);
        List<ChatRoom> chatRoomsForLending = chatRoomRepository.findAllByLender(user);

        List<ChatRoomsResponse> chatRoomsDTO = new ArrayList<>();

        String lastMessage;
        LocalDateTime lastMeesageTime;
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
        return chatRoomsDTO;
    }

    public ChatRoomResponse createRoom(AddChatRoomRequest request) {
        String roomId = UUID.randomUUID().toString();
        User borrower = userRepository.findById(request.getBorrowerId()).get();
        User lender = userRepository.findById(request.getRenderId()).get();
        Post post = postRepository.findById(request.getPostId()).get();
        ChatRoom chatRoom = ChatRoom.builder()
                .roomId(roomId)
                .borrower(borrower)
                .lender(lender)
                .post(post)
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
