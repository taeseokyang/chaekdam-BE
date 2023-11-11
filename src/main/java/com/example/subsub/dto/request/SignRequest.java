package com.example.subsub.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignRequest {
    private Long id;

    private String userid;

    private String nickname;

    private String password;
}
