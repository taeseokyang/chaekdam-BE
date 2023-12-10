package com.example.subsub.dto.response;

import com.example.subsub.domain.Council;
import com.example.subsub.domain.CouncilItem;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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
    private List<CouncilItem> items = new ArrayList<>();
    @Builder
    public CouncilResponse(Council council){
        councilId = council.getCouncilId();
        name = council.getName();
        location = council.getLocation();
        operatingHours = council.getOperatingHours();
        usageGuidelines = council.getUsageGuidelines();
        items = council.getItems();
    }
}
