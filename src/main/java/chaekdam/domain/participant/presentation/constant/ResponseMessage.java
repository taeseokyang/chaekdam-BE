package chaekdam.domain.participant.presentation.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {
    ALREADY_PARTICIPATING("이미 참여하고 있습니다."),
    PARTICIPANT_NOT_FOUND("참여자를 찾을 수 없습니다.")
    ;
    private String message;
}