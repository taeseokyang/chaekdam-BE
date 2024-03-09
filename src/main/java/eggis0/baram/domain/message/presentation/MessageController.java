package eggis0.baram.domain.message.presentation;

import eggis0.baram.domain.message.application.MessageService;
import eggis0.baram.domain.message.domain.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/message")
public class MessageController {

    private final MessageService messageService;

    @GetMapping("/{roomId}")
    public ResponseEntity<List<Message>> getAllMessageByRoom(@PathVariable String roomId, Authentication authentication) throws Exception {
        if (authentication == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        List<Message> messages = messageService.getAllMessageByRoomId(roomId, authentication.getName());
        return ResponseEntity.ok(messages);
    }
}