package eggis0.baram.controller;

import eggis0.baram.domain.Message;
import eggis0.baram.dto.chat.AddChatRoomRequest;
import eggis0.baram.dto.chat.ChatRoomResponse;
import eggis0.baram.dto.response.ChatRoomsResponse;
import eggis0.baram.service.ChatService;
import eggis0.baram.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;
    private final MessageService messageService;

    @PostMapping
    public ChatRoomResponse createRoom(@RequestBody AddChatRoomRequest request){
        return chatService.createRoom(request);
    }

    @GetMapping("/message/{roomId}")
    public ResponseEntity<List<Message>> getAllMessageByRoom(@PathVariable String roomId, Authentication authentication) throws Exception {
        if (authentication == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        List<Message> messages = messageService.getAllMessageByRoomId(roomId, authentication.getName());
        return ResponseEntity.ok(messages);
    }
//    @GetMapping
//    public List<ChatRoom> findAllRooms(){
//        return chatService.findAllRoom();
//    }

    @GetMapping("/user")
    public ResponseEntity<List<List<ChatRoomsResponse>> > findAllByUser(Authentication authentication){
        if (authentication == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        return ResponseEntity.ok(chatService.findAllByUser(authentication.getName()));
    }
}