package eggis0.baram.domain2.council_item.dto.req;

import eggis0.baram.domain2.council_item.domain.ItemType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddCouncilItemRequest {
    private Integer councilId;
    private String name;
    private ItemType type;
}
