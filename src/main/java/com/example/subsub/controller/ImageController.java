package com.example.subsub.controller;

import com.example.subsub.dto.request.UpdateUserCertifiRequest;
import com.example.subsub.dto.request.UpdateUserRequest;
import com.example.subsub.dto.request.UserRequest;
import com.example.subsub.dto.response.RegisterResponse;
import com.example.subsub.dto.response.UserResponse;
import com.example.subsub.service.UserService;
import com.example.subsub.utils.FilePath;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class ImageController {
    @GetMapping("/image/{path}")
    public ResponseEntity<?> returnImage(@PathVariable String path) throws Exception {
        Resource resource = new FileSystemResource(FilePath.IMAGEPATH + path);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }


}