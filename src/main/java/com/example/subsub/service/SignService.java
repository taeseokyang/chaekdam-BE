package com.example.subsub.service;

import com.example.subsub.domain.Authority;
import com.example.subsub.domain.User;
import com.example.subsub.dto.response.RegisterResponse;
import com.example.subsub.dto.request.SignRequest;
import com.example.subsub.dto.response.SignResponse;
import com.example.subsub.repository.UserRepository;
import com.example.subsub.security.JwtProvider;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@Transactional
@RequiredArgsConstructor
public class SignService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Transactional
    public ResponseEntity<SignResponse> login(SignRequest request) throws Exception {
        String messsage;
        if (userRepository.countUserByUserId(request.getUserid())==1){
            User user = userRepository.findByUserId(request.getUserid()).get();

            if (!passwordEncoder.matches(request.getPassword(), user.getPassWord())) {
                messsage = "비밀 번호가 틀립니다.";

                SignResponse signResponse = SignResponse.builder()
                        .id(null)
                        .userId(null)
                        .nickname(null)
                        .roles(null)
                        .token(null)
                        .message(messsage)
                        .build();
                return new ResponseEntity<>(signResponse, HttpStatus.UNAUTHORIZED);
            }
            SignResponse signResponse= SignResponse.builder()
                    .id(user.getId())
                    .userId(user.getUserId())
                    .nickname(user.getNickName())
                    .roles(user.getRoles())
                    .token(jwtProvider.createToken(user.getUserId(), user.getRoles()))
                    .message("로그인 성공")
                    .build();
            return new ResponseEntity<>(signResponse, HttpStatus.OK);

        }else{
            messsage = "계정이 존재하지 않습니다.";
            SignResponse signResponse=  SignResponse.builder()
                    .id(null)
                    .userId(null)
                    .nickname(null)
                    .roles(null)
                    .token(null)
                    .message(messsage)
                    .build();
            return new ResponseEntity<>(signResponse, HttpStatus.NOT_FOUND);
        }
    }
    @Transactional
    public ResponseEntity<RegisterResponse> register(SignRequest request) throws Exception {
        try {
            User user = User.builder()
                    .userId(request.getUserid())
                    .passWord(passwordEncoder.encode(request.getPassword()))
                    .nickName(request.getNickname())
                    .build();

            user.setRoles(Collections.singletonList(Authority.builder().name("ROLE_USER").build()));

            if (userRepository.countUserByUserId(user.getUserId())==0){
                userRepository.save(user);
            }else{
                return new ResponseEntity<>(new RegisterResponse(false, "중복된 ID"), HttpStatus.CONFLICT);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
        return new ResponseEntity<>(new RegisterResponse(true, "회원가입 성공"), HttpStatus.OK);
    }
    @Transactional
    public ResponseEntity<SignResponse> getUser(String id) throws Exception {
        if (userRepository.countUserByUserId(id)==1){
            User user = userRepository.findByUserId(id).get();
            return new ResponseEntity<>(new SignResponse(user, "계정 조회 성공"), HttpStatus.OK);
        }else{
            SignResponse signResponse = SignResponse.builder()
                    .id(null)
                    .userId(null)
                    .nickname(null)
                    .roles(null)
                    .token(null)
                    .message("계정이 존재하지 않습니다.")
                    .build();
            return new ResponseEntity<>(signResponse, HttpStatus.NOT_FOUND);
        }

    }

}