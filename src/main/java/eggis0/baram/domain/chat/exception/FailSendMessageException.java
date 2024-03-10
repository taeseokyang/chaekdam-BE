package eggis0.baram.domain.chat.exception;

import eggis0.baram.global.config.exception.ApplicationException;

import static eggis0.baram.domain.chat.presentation.constant.ResponseMessage.FAIL_SEND_MESSAGE;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class FailSendMessageException extends ApplicationException {
    public FailSendMessageException() {
        super(INTERNAL_SERVER_ERROR.value(), FAIL_SEND_MESSAGE.getMessage());
    }
}
