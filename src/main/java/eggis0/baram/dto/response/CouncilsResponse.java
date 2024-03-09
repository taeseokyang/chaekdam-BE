package eggis0.baram.dto.response;

import eggis0.baram.domain.Council;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CouncilsResponse {

    private Integer councilId;
    private String college;
    private String name;
    private String imgPath;
    private Integer providedItemCount;
    private Integer rentalItemCount;
    private Boolean isCouncilSelfManage;
    @Builder
    public CouncilsResponse(Council council, Integer pic, Integer ric, String imgPath){
        councilId = council.getCouncilId();
        college = council.getCollege();
        name = council.getName();
        providedItemCount = pic;
        rentalItemCount = ric;
        this.imgPath = imgPath;
        isCouncilSelfManage = council.getIsCouncilSelfManage();
    }
}
