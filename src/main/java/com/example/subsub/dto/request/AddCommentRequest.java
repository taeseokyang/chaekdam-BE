package com.example.subsub.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class AddCommentRequest {
    private final Integer postId;
    private final String text;
    private final LocalDateTime createdAt;
    private final Boolean isSecret;

}
