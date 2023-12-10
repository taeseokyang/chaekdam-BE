package com.example.subsub.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class AddPostRequest {
    private String title;
    private String location;
    private String locationDetail;
    private long rentalFee;
    private LocalDateTime createdAt;
    private LocalDate needAt;
    private LocalDate returnAt;
    private String content;
}
