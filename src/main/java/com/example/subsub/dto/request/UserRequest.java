package com.example.subsub.dto.request;

import com.example.subsub.domain.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
    private Long id;

    private String userid;

    private String nickname;

    private String password;

    private Role role;
}
