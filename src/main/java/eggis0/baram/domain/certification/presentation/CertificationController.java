package eggis0.baram.domain.certification.presentation;

import eggis0.baram.domain.certification.application.CertificationService;
import eggis0.baram.domain.certification.dto.req.CertifiRequest;
import eggis0.baram.domain.certification.dto.req.UpdateUserCertifiRequest;
import eggis0.baram.domain.certification.dto.res.CertifiResponse;
import eggis0.baram.domain.user.application.UserService;
import eggis0.baram.global.config.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static eggis0.baram.domain.announcement.presentation.constant.ResponseMessage.*;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/certifi") // certifications
@RequiredArgsConstructor
public class CertificationController {

    private final CertificationService certificationService;
    private final UserService userService;

    @PostMapping("/request") //
    public ResponseDto<CertifiResponse> save(@RequestPart CertifiRequest request, @RequestPart(required = false) MultipartFile pic, Authentication authentication) throws Exception {
        CertifiResponse response = certificationService.save(request, pic, authentication.getName());
        return ResponseDto.of(OK.value(), SUCCESS_CREATE.getMessage(), response);
    }

    @GetMapping("/requests/{id}") // {id}
    public ResponseDto<CertifiResponse> get(@PathVariable Long id) throws Exception {
        CertifiResponse response = certificationService.get(id);
        return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), response);
    }

    @GetMapping("/requests") //
    public ResponseDto<List<CertifiResponse>> getAll() {
        List<CertifiResponse> responses = certificationService.getAll();
        return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), responses);
    }

    @PutMapping("/approval/{id}") // {id}
    public ResponseDto update(@PathVariable String id, @RequestBody UpdateUserCertifiRequest request) {
        userService.updateCertification(id, request);
        return ResponseDto.of(OK.value(), SUCCESS_UPDATE.getMessage());
    }
}