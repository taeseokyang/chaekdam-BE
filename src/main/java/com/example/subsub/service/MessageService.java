package com.example.subsub.service;

import com.example.subsub.domain.*;
import com.example.subsub.repository.ChatRoomRepository;
import com.example.subsub.repository.MessageRepository;
import com.example.subsub.repository.PostRepository;
import com.example.subsub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
