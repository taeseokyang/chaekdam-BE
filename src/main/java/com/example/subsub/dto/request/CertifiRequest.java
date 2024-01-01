package com.example.subsub.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CertifiRequest {
    private String userid;
    private LocalDateTime requestAt;
}
