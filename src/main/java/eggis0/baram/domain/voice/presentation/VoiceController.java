package eggis0.baram.domain.voice.presentation;

import eggis0.baram.domain.voice.application.VoiceService;
import eggis0.baram.domain.voice.dto.req.VoiceRequest;
import eggis0.baram.domain.voice.dto.res.VoicesResponse;
import eggis0.baram.global.config.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static eggis0.baram.domain.announcement.presentation.constant.ResponseMessage.SUCCESS_CREATE;
import static eggis0.baram.domain.council.presentation.constant.ResponseMessage.SUCCESS_READ;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/voice")
@RequiredArgsConstructor
public class VoiceController {
    private final VoiceService voiceService;

    @PostMapping
    public ResponseDto save(@RequestBody VoiceRequest request) throws Exception {
        voiceService.save(request);
        return ResponseDto.of(OK.value(), SUCCESS_CREATE.getMessage());
    }

    @GetMapping()
    public ResponseDto<List<VoicesResponse>> get() {
        List<VoicesResponse> response = voiceService.get();
        return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), response);
    }
}