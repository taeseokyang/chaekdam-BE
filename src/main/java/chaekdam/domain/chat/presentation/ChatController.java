package chaekdam.domain.chat.presentation;

import chaekdam.domain.chat.application.ChatService;
import chaekdam.domain.chat.dto.res.ChatRoomResponse;
import chaekdam.domain.chat.dto.res.ChatRoomsResponse;
import chaekdam.global.config.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static chaekdam.domain.chat.presentation.constant.ResponseMessage.SUCCESS_CREATE;
import static chaekdam.domain.chat.presentation.constant.ResponseMessage.SUCCESS_READ;
import static org.springframework.http.HttpStatus.OK;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;

//    @PostMapping("/{isbn}")
//    public ResponseDto<ChatRoomResponse> save(@PathVariable String isbn) {
//        ChatRoomResponse response = chatService.save(isbn);
//        return ResponseDto.of(OK.value(), SUCCESS_CREATE.getMessage(), response);
//    }

    @GetMapping("/user")
    public ResponseDto<List<ChatRoomsResponse>> getAllByUser(Authentication authentication) {
        List<ChatRoomsResponse> responses = chatService.getAllByUser(authentication.getName());
        return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), responses);
    }
}