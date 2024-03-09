package com.example.subsub.controller;

import com.example.subsub.domain.Certification;
import com.example.subsub.dto.request.CertifiRequest;
import com.example.subsub.dto.request.UpdateUserCertifiRequest;
import com.example.subsub.dto.response.UserResponse;
import com.example.subsub.service.CertificationService;
import com.example.subsub.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.parser.Authorization;
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
    public ResponseEntity<UserResponse>  updateCertification(@PathVariable String id,@RequestBody UpdateUserCertifiRequest request) {
        return userService.updateCertification(id, request);
    }
}