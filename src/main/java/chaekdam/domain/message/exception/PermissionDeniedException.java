package chaekdam.domain.message.exception;

import chaekdam.domain.chat.presentation.constant.ResponseMessage;
import chaekdam.global.config.exception.ApplicationException;

import static org.springframework.http.HttpStatus.FORBIDDEN;

public class PermissionDeniedException extends ApplicationException {
    public PermissionDeniedException() {
        super(FORBIDDEN.value(), ResponseMessage.FAIL_SEND_MESSAGE.getMessage());
    }
}
