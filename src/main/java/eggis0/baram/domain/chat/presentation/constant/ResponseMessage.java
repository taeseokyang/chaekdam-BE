package eggis0.baram.domain.chat.presentation.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {
    SUCCESS_CREATE("생성에 성공했습니다."),
    SUCCESS_READ("조회에 성공했습니다."),
    SUCCESS_UPDATE("수정에 성공했습니다."),
    SUCCESS_DELETE("삭제에 성공했습니다."),

    FAIL_SEND_MESSAGE("메세지 전송에 실패 했습니다."),
    ;
    private String message;
}