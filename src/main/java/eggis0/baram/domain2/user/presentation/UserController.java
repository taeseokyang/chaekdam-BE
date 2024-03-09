package eggis0.baram.domain2.user.presentation;

import eggis0.baram.domain2.user.dto.req.UserRequest;
import eggis0.baram.domain2.user.dto.res.UserResponse;
import eggis0.baram.domain2.user.application.UserService;
import eggis0.baram.domain2.user.dto.req.UpdateUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping(value = "/login")
    public ResponseEntity<UserResponse> signin(@RequestBody UserRequest request) throws Exception {
        return userService.login(request);
    }

//    @PostMapping(value = "/register")
//    public ResponseEntity<RegisterResponse> signup(@RequestPart UserRequest request, @RequestPart(required = false) MultipartFile pic) throws Exception {
//        return userService.register(request, pic);
//    }

    @GetMapping("/account")
    public ResponseEntity<UserResponse> getUser(@RequestParam String id) throws Exception {
        return userService.getUser(id);
    }

    @GetMapping("/admin")
    public ResponseEntity<UserResponse> getUserForAdmin(@RequestParam String id) throws Exception {
        return userService.getUser(id);
    }

//    @PutMapping("/admin/user/certification")
//    public ResponseEntity<UserResponse>  updateCertification(@RequestParam String id,@RequestBody UpdateUserCertifiRequest request) {
//        return userService.updateCertification(id, request);
//    }
    @PutMapping("/account/update")
    public ResponseEntity<UserResponse>  updateNicknameAndPhoto(@RequestPart UpdateUserRequest request, @RequestPart(required = false) MultipartFile pic, Authentication authentication) {
        if (authentication == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        return userService.updateNicknameAndPhoto(authentication.getName(), pic, request);
    }


}