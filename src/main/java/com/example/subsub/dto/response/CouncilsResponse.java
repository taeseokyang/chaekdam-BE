package com.example.subsub.dto.response;

import com.example.subsub.domain.Council;
import com.example.subsub.domain.CouncilItem;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CouncilsResponse {

    private Integer councilId;
    private String college;
    private String name;
    private Integer providedItemCount;
    private Integer rentalItemCount;
    @Builder
    public CouncilsResponse(Council council, Integer pic, Integer ric){
        councilId = council.getCouncilId();
        college = council.getCollege();
        name = council.getName();
        providedItemCount = pic;
        rentalItemCount = ric;
    }
}
