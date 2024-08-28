package eggis0.baram.domain.voice.dto.res;

import eggis0.baram.domain.voice.domain.Voice;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VoicesResponse {

    private Long voiceId;
    private String opinion;

    @Builder
    public VoicesResponse(Voice voice) {
        voiceId = voice.getVoiceId();
        opinion = voice.getOpinion();
    }
}
