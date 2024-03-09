package eggis0.baram.domain.chat.presentation;

import eggis0.baram.domain.chat.application.ChatService;
import eggis0.baram.domain.chat.dto.req.AddChatRoomRequest;
import eggis0.baram.domain.chat.dto.res.ChatRoomResponse;
import eggis0.baram.domain.chat.dto.res.ChatRoomsResponse;
import eggis0.baram.global.config.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static eggis0.baram.domain.chat.presentation.constant.ResponseMessage.SUCCESS_CREATE;
import static org.springframework.http.HttpStatus.OK;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;

    @PostMapping
    public ResponseDto<ChatRoomResponse> save(@RequestBody AddChatRoomRequest request) {
        ChatRoomResponse response = chatService.save(request);
        return ResponseDto.of(OK.value(), SUCCESS_CREATE.getMessage(), response);
    }

    @GetMapping("/user")
    public ResponseDto<List<List<ChatRoomsResponse>>> getAllByUser(Authentication authentication) {
        List<List<ChatRoomsResponse>> responses = chatService.getAllByUser(authentication.getName());
        return ResponseDto.of(OK.value(), SUCCESS_CREATE.getMessage(), responses);
    }
}