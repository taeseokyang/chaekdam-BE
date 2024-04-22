package eggis0.baram.domain.gachon_herald.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class GachonHeraldRequest {
    private String opinion;
}
