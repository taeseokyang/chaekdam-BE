package com.example.subsub.service;

import com.example.subsub.domain.Authority;
import com.example.subsub.domain.User;
import com.example.subsub.dto.response.RegisterResponse;
import com.example.subsub.dto.request.SignRequest;
import com.example.subsub.dto.response.SignResponse;
import com.example.subsub.repository.UserRepository;
import com.example.subsub.security.JwtProvider;
import lombok.RequiredArgsConstructor;

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
    public SignResponse login(SignRequest request) throws Exception {
        String messsage;
        if (userRepository.countUserByUserId(request.getUserid())==1){
            User user = userRepository.findByUserId(request.getUserid()).get();

            if (!passwordEncoder.matches(request.getPassword(), user.getPassWord())) {
                messsage = "비밀 번호가 틀립니다.";
                return SignResponse.builder()
                        .id(null)
                        .userId(null)
                        .nickname(null)
                        .roles(null)
                        .token(null)
                        .message(messsage)
                        .build();
            }
            return SignResponse.builder()
                    .id(user.getId())
                    .userId(user.getUserId())
                    .nickname(user.getNickName())
                    .roles(user.getRoles())
                    .token(jwtProvider.createToken(user.getUserId(), user.getRoles()))
                    .message("로그인 성공")
                    .build();
        }else{
            messsage = "계정이 존재하지 않습니다.";
            return SignResponse.builder()
                    .id(null)
                    .userId(null)
                    .nickname(null)
                    .roles(null)
                    .token(null)
                    .message(messsage)
                    .build();
        }
    }
    @Transactional
    public RegisterResponse register(SignRequest request) throws Exception {
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
                return new RegisterResponse(false, "중복된 ID");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
        return new RegisterResponse(true, "회원가입 성공");
    }
    @Transactional
    public SignResponse getUser(String id) throws Exception {
        if (userRepository.countUserByUserId(id)==1){
            User user = userRepository.findByUserId(id).get();
            return new SignResponse(user, "계정 조회 성공");
        }else{
            return SignResponse.builder()
                    .id(null)
                    .userId(null)
                    .nickname(null)
                    .roles(null)
                    .token(null)
                    .message("계정이 존재하지 않습니다.")
                    .build();
        }

    }

}