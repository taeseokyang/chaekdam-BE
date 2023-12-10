package com.example.subsub.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class UpdateCouncilRequest {
    private String name;
    private String location;
    private String operatingHours;
    private String usageGuidelines;
}
