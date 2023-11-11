package com.example.subsub.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class AddCommentRequest {
    private final Integer postId;
    private final String text;
    private final LocalDate createdAt;
    private final Boolean isSecret;

}
