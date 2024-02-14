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
    private String college;
    private String name;
    private String location;
    private String operatingHours;
    private String usageGuidelines;
    private String imgPath;
    private List<CouncilItem> items = new ArrayList<>();
    @Builder
    public CouncilResponse(Council council, String imgPath){
        councilId = council.getCouncilId();
        college = council.getCollege();
        name = council.getName();
        location = council.getLocation();
        operatingHours = council.getOperatingHours();
        usageGuidelines = council.getUsageGuidelines();
        this.imgPath = imgPath;
        items = council.getItems();
    }

    @Builder
    public CouncilResponse(Council council){
        councilId = council.getCouncilId();
        college = council.getCollege();
        name = council.getName();
        location = council.getLocation();
        operatingHours = council.getOperatingHours();
        usageGuidelines = council.getUsageGuidelines();
        items = council.getItems();
    }
}
