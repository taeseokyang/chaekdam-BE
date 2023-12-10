package com.example.subsub.dto.response;

import com.example.subsub.domain.Council;
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
    private ItemType type;
    @Builder
    public CouncilItemResponse(CouncilItem councilItem){
        councilId = councilItem.getCouncil().getCouncilId();
        name = councilItem.getName();
        type = councilItem.getType();
    }
}
