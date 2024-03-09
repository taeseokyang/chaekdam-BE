package eggis0.baram.domain2.certification.presentation;

import eggis0.baram.domain2.certification.dto.req.CertifiRequest;
import eggis0.baram.domain2.certification.application.CertificationService;
import eggis0.baram.domain2.certification.dto.req.UpdateUserCertifiRequest;
import eggis0.baram.domain2.certification.domain.Certification;
import eggis0.baram.domain2.user.dto.res.UserResponse;
import eggis0.baram.domain2.user.application.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/certifi")
@RequiredArgsConstructor
public class CertificationController {

    private final CertificationService certificationService;
    private final UserService userService;


    @PostMapping("/request")
    public ResponseEntity<Certification> request(@RequestPart CertifiRequest request, @RequestPart(required = false) MultipartFile pic, Authentication authentication) throws Exception {
        return certificationService.request(request, pic, authentication.getName());
    }

    @GetMapping("/requests/{id}")
    public ResponseEntity<Certification> getRequest(@PathVariable Long id) throws Exception {
        return certificationService.getRequest(id);
    }
    @GetMapping("/requests")
    public ResponseEntity<List<Certification>> getRequests() throws Exception {
        return certificationService.requests();
    }

    @PutMapping("/approval/{id}")
    public ResponseEntity<UserResponse>  updateCertification(@PathVariable String id, @RequestBody UpdateUserCertifiRequest request) {
        return userService.updateCertification(id, request);
    }
}