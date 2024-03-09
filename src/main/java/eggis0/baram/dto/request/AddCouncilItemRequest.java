package eggis0.baram.dto.request;

import eggis0.baram.domain.ItemType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddCouncilItemRequest {
    private Integer councilId;
    private String name;
    private ItemType type;
}
