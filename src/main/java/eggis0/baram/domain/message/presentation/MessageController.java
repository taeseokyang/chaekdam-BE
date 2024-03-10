package eggis0.baram.domain.message.presentation;

import eggis0.baram.domain.message.application.MessageService;
import eggis0.baram.domain.message.dto.res.MessageResponse;
import eggis0.baram.global.config.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static eggis0.baram.domain.message.presentation.constant.ResponseMessage.SUCCESS_READ;
import static org.springframework.http.HttpStatus.OK;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/message")
public class MessageController {

    private final MessageService messageService;

    @GetMapping("/{roomId}")
    public ResponseDto<List<MessageResponse>> getAllByRoom(@PathVariable String roomId, Authentication authentication) {
        List<MessageResponse> responses = messageService.getAllMessageByRoomId(roomId, authentication.getName());
        return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), responses);
    }
}