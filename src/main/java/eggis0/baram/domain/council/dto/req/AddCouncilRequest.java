package eggis0.baram.domain.council.dto.req;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddCouncilRequest {
    private String college;
    private String name;
    private String location;
    private String operatingHours;
    private String usageGuidelines;
}