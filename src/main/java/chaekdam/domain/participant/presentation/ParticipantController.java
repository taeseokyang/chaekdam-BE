package chaekdam.domain.participant.presentation;

import chaekdam.domain.message.application.MessageService;
import chaekdam.domain.message.dto.res.MessageResponse;
import chaekdam.domain.participant.application.ParticipantService;
import chaekdam.global.config.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static chaekdam.domain.message.presentation.constant.ResponseMessage.SUCCESS_CREATE;
import static chaekdam.domain.message.presentation.constant.ResponseMessage.SUCCESS_READ;
import static org.springframework.http.HttpStatus.OK;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/participant")
public class ParticipantController {

    private final ParticipantService participantService;

    @PostMapping("/{isbn}")
    public ResponseDto participate(@PathVariable String isbn, Authentication authentication) {
        participantService.participate(isbn, authentication.getName());
        return ResponseDto.of(OK.value(), SUCCESS_CREATE.getMessage());
    }
}