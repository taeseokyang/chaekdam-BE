package chaekdam.domain.message.presentation;

import chaekdam.domain.message.application.MessageService;
import chaekdam.global.config.dto.ResponseDto;
import chaekdam.domain.message.dto.res.MessageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static chaekdam.domain.message.presentation.constant.ResponseMessage.SUCCESS_READ;
import static org.springframework.http.HttpStatus.OK;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/message")
public class MessageController {

    private final MessageService messageService;

    // 메세지 조회
    @GetMapping("/{roomId}")
    public ResponseDto<List<MessageResponse>> getAllByRoom(@PathVariable String roomId, @RequestParam Long lastMessageId) {
        List<MessageResponse> responses = messageService.getAllMessageByRoomId(roomId, lastMessageId);
        return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), responses);
    }
}