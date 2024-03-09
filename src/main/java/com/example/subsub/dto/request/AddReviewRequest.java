package com.example.subsub.dto.request;

import com.example.subsub.domain.RateType;
import com.example.subsub.domain.User;
import com.example.subsub.domain.UserType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class AddReviewRequest {
    private Integer postId;
    private UserType writerType;
    private String recipientId;
    private RateType rate;
    private String text;
}
