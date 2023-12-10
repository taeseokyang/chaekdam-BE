package com.example.subsub.dto.response;

import com.example.subsub.domain.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private Long id;
    private String userId;
    private String nickname;
    private Role roles;
    private String token;
    private boolean isCertification;
    private String message;

    public UserResponse(User user, String message) {
        this.id = user.getId();
        this.userId = user.getUserId();
        this.nickname = user.getNickName();
        this.roles = user.getRoles();
        this.isCertification = user.isCertification();
        this.message = message;
    }
}