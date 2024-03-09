package eggis0.baram.domain2.council.dto.req;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateCouncilRequest {
    private String college;
    private String name;
    private String location;
    private String operatingHours;
    private String usageGuidelines;
    private boolean isCouncilSelfManage;
}
