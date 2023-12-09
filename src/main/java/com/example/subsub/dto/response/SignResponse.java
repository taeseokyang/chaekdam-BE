package com.example.subsub.dto.response;

import com.example.subsub.domain.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignResponse {

    private Long id;
    private String userId;
    private String nickname;
    private Role roles;
    private String token;
    private String message;

    public SignResponse(User user, String message) {
        this.id = user.getId();
        this.userId = user.getUserId();
        this.nickname = user.getNickName();
        this.roles = user.getRoles();
        this.message = message;

    }
}