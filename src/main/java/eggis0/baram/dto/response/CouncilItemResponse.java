package eggis0.baram.dto.response;

import eggis0.baram.domain.CouncilItem;
import eggis0.baram.domain.ItemType;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CouncilItemResponse {
    private Integer councilId;
    private String name;
    private Integer quantity;
    private ItemType type;
    @Builder
    public CouncilItemResponse(CouncilItem councilItem){
        councilId = councilItem.getCouncil().getCouncilId();
        name = councilItem.getName();
        quantity = councilItem.getQuantity();
        type = councilItem.getType();
    }
}
