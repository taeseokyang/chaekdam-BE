package eggis0.baram.domain2.council_item.dto.res;

import eggis0.baram.domain2.council_item.domain.ItemType;
import eggis0.baram.domain2.council_item.domain.CouncilItem;
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
