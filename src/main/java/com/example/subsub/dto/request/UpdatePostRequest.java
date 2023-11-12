package com.example.subsub.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class UpdatePostRequest {
    private String title;
    private String location;
    private String locationDetail;
    private long rentalFee;
    private LocalDate needAt;
    private LocalDate returnAt;
    private String content;
}
