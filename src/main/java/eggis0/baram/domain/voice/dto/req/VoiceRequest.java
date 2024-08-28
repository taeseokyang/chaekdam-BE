package eggis0.baram.domain.voice.dto.req;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class VoiceRequest {
    private String phoneNumber;
    private String opinion;
}
