package eggis0.baram.dto.response;

import eggis0.baram.domain.Council;
import eggis0.baram.domain.CouncilItem;
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
    private Boolean isCouncilSelfManage;
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
        isCouncilSelfManage = council.getIsCouncilSelfManage();
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
        isCouncilSelfManage = council.getIsCouncilSelfManage();
    }
}
