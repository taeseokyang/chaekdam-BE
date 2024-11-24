package chaekdam.domain.participant.dto.req;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddParticipantRequest {
    private String roomId;
    private String userId;
}
