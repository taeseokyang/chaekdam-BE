package eggis0.baram.domain.gachon_herald.presentation;

import eggis0.baram.domain.certification.dto.res.CertifiResponse;
import eggis0.baram.domain.gachon_herald.application.GachonHeraldService;
import eggis0.baram.domain.gachon_herald.dto.GachonHeraldRequest;
import eggis0.baram.global.config.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static eggis0.baram.domain.announcement.presentation.constant.ResponseMessage.SUCCESS_CREATE;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/gachon-herald")
@RequiredArgsConstructor
public class GachonHeraldController {

    private final GachonHeraldService gachonHeraldService;

    @PostMapping
    public ResponseDto<CertifiResponse> save(@RequestBody GachonHeraldRequest request, Authentication authentication) throws Exception {
        gachonHeraldService.save(request, authentication.getName());
        return ResponseDto.of(OK.value(), SUCCESS_CREATE.getMessage());
    }
}