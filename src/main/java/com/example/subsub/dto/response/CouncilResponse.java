package com.example.subsub.dto.response;

import com.example.subsub.domain.Council;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CouncilResponse {

    private Integer councilId;
    private String name;
    private String location;
    private String operatingHours;
    private String usageGuidelines;

    @Builder
    public CouncilResponse(Council council){
        councilId = council.getCouncilId();
        name = council.getName();
        location = council.getLocation();
        operatingHours = council.getOperatingHours();
        usageGuidelines = council.getUsageGuidelines();
    }
}
