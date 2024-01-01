package com.example.subsub.dto.response;

import com.example.subsub.domain.CouncilItem;
import com.example.subsub.domain.ItemType;
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
