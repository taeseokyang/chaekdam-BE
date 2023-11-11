package com.example.subsub.dto.request;

import com.example.subsub.domain.Comment;
import com.example.subsub.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
public class AddPostRequest {
    private String title;
    private String location;
    private String locationDetail;
    private long rentalFee;
    private LocalDate createdAt;
    private LocalDate needAt;
    private LocalDate returnAt;
    private String content;
    private Boolean isClose;
}
