package com.example.subsub.controller;


import com.example.subsub.domain.ChatRoom;
import com.example.subsub.dto.chat.AddChatRoomRequest;
import com.example.subsub.dto.chat.ChatRoomResponse;
import com.example.subsub.dto.response.ChatRoomsResponse;
import com.example.subsub.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;

    @PostMapping
    public ChatRoomResponse createRoom(@RequestBody AddChatRoomRequest request){
        return chatService.createRoom(request);
    }

    @GetMapping
    public List<ChatRoom> findAllRooms(){
        return chatService.findAllRoom();
    }

    @GetMapping("/user")
    public List<ChatRoomsResponse> findAllByUser(Authentication authentication){
        return chatService.findAllByUser(authentication.getName());
    }
}