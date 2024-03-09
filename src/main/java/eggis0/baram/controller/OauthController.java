package eggis0.baram.controller;

import eggis0.baram.dto.request.KakaoSignUpRequest;
import eggis0.baram.dto.response.UserResponse;
import eggis0.baram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth")
public class OauthController {
    private final UserService userService;
    @GetMapping(value = "/kakao/{code}")
    public ResponseEntity<UserResponse> signin(@PathVariable String code) throws Exception {
        System.out.println(code);
        return userService.kakaoLogin(code);
    }

    @PostMapping(value = "/kakao")
    public ResponseEntity<UserResponse> signUp(@RequestBody KakaoSignUpRequest request) throws Exception {
        return userService.kakaoRegister(request.getNickname(), request.getEmail());
    }
}
