package eggis0.baram.domain.message.exception;

import eggis0.baram.global.config.exception.ApplicationException;

import static eggis0.baram.domain.chat.presentation.constant.ResponseMessage.FAIL_SEND_MESSAGE;
import static org.springframework.http.HttpStatus.FORBIDDEN;

public class PermissionDeniedException extends ApplicationException {
    public PermissionDeniedException() {
        super(FORBIDDEN.value(), FAIL_SEND_MESSAGE.getMessage());
    }
}
