package chaekdam.domain.chat.exception;

import chaekdam.domain.chat.presentation.constant.ResponseMessage;
import chaekdam.global.config.exception.ApplicationException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class FailSendMessageException extends ApplicationException {
    public FailSendMessageException() {
        super(INTERNAL_SERVER_ERROR.value(), ResponseMessage.FAIL_SEND_MESSAGE.getMessage());
    }
}
