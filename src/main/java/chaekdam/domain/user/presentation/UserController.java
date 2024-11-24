package chaekdam.domain.user.presentation;

import chaekdam.domain.user.application.UserService;
import chaekdam.domain.user.dto.res.CheckResponse;
import chaekdam.global.config.dto.ResponseDto;
import chaekdam.domain.user.dto.req.UpdateUserRequest;
import chaekdam.domain.user.dto.req.UserRequest;
import chaekdam.domain.user.dto.res.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static chaekdam.domain.user.presentation.constant.ResponseMessage.*;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // 로그인
    @PostMapping(value = "/login")
    public ResponseDto<UserResponse> signIn(@RequestBody UserRequest request) {
        UserResponse response = userService.login(request);
        return ResponseDto.of(OK.value(), SUCCESS_LOGIN.getMessage(), response);
    }

    // 회원가입
    @PostMapping(value = "/register")
    public ResponseDto<UserResponse> signUp(@RequestPart(required = false) MultipartFile pic, @RequestPart UserRequest request) {
        UserResponse response = userService.register(request, pic);
        return ResponseDto.of(OK.value(), SUCCESS_REGISTER.getMessage(), response);
    }

    // 계정 존재 확인
    @GetMapping(value = "/check/{id}")
    public ResponseDto<CheckResponse> check(@PathVariable String id) {
        CheckResponse response = userService.check(id);
        return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), response);
    }

    // 계정 조회
    @GetMapping("/account")
    public ResponseDto<UserResponse> get(@RequestParam Long id) {
        UserResponse response = userService.get(id);
        return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), response);
    }
}