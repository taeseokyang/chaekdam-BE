package com.example.subsub.service;

import com.example.subsub.repository.UserRepository;
import com.example.subsub.security.JwtProvider;
import com.example.subsub.domain.Role;
import com.example.subsub.domain.User;
import com.example.subsub.dto.request.UpdateUserCertifiRequest;
import com.example.subsub.dto.request.UpdateUserRequest;
import com.example.subsub.dto.response.RegisterResponse;
import com.example.subsub.dto.request.UserRequest;
import com.example.subsub.dto.response.UserResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public ResponseEntity<UserResponse> login(UserRequest request) throws Exception {
        if (userRepository.existsUserByUserId(request.getUserid())){
            User user = userRepository.findByUserId(request.getUserid()).get();

            if (!passwordEncoder.matches(request.getPassword(), user.getPassWord())) {
                UserResponse signResponse = UserResponse.builder()
                        .message("비밀 번호가 틀립니다.")
                        .build();
                return new ResponseEntity<>(signResponse, HttpStatus.UNAUTHORIZED);
            }
            UserResponse signResponse= UserResponse.builder()
                    .id(user.getId())
                    .userId(user.getUserId())
                    .nickname(user.getNickName())
                    .roles(user.getRoles())
                    .token(jwtProvider.createToken(user.getUserId(), user.getRoles()))
                    .message("로그인 성공")
                    .build();
            return new ResponseEntity<>(signResponse, HttpStatus.OK);

        }
        UserResponse signResponse=  UserResponse.builder()
                .message("계정이 존재하지 않습니다.")
                .build();
        return new ResponseEntity<>(signResponse, HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<RegisterResponse> register(UserRequest request) throws Exception {
        try {
            User user = User.builder()
                    .userId(request.getUserid())
                    .passWord(passwordEncoder.encode(request.getPassword()))
                    .nickName(request.getNickname())
                    .build();

            user.setRoles(Role.valueOf("USER"));
            if (userRepository.existsUserByUserId(user.getUserId())){
                return new ResponseEntity<>(new RegisterResponse(false, "중복된 ID"), HttpStatus.CONFLICT);
            }
            userRepository.save(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
        return new ResponseEntity<>(new RegisterResponse(true, "회원가입 성공"), HttpStatus.OK);
    }

    public ResponseEntity<UserResponse> getUser(String userId) throws Exception {
        if (userRepository.existsUserByUserId(userId)){
            User user = userRepository.findByUserId(userId).get();
            return new ResponseEntity<>(new UserResponse(user, "계정 조회 성공"), HttpStatus.OK);
        }else{
            UserResponse signResponse = UserResponse.builder()
                    .message("계정이 존재하지 않습니다.")
                    .build();
            return new ResponseEntity<>(signResponse, HttpStatus.NOT_FOUND);
        }

    }

    public ResponseEntity<UserResponse> updateNickname(String userId, UpdateUserRequest request) {
        User user = userRepository.findByUserId(userId).orElseThrow(IllegalArgumentException::new);
        user.setNickName(request.getNickname());
        User updatedUser = userRepository.save(user);
        return ResponseEntity.ok(new UserResponse(updatedUser, "변경 성공"));
    }

    public ResponseEntity<UserResponse> updateCertification(String userId, UpdateUserCertifiRequest request) {
        User user = userRepository.findByUserId(userId).orElseThrow(IllegalArgumentException::new);
        user.setCertification(request.getIsCertification());
        User updatedUser = userRepository.save(user);
        return ResponseEntity.ok(new UserResponse(updatedUser, "변경 성공"));
    }
}