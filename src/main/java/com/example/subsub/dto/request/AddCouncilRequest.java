package com.example.subsub.dto.request;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class AddCouncilRequest {
    private String college;
    private String name;
    private String location;
    private String operatingHours;
    private String usageGuidelines;
}
