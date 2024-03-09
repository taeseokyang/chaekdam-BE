package eggis0.baram.domain.chat.exception;

import eggis0.baram.global.config.exception.ApplicationException;

import static eggis0.baram.domain.chat.presentation.constant.ResponseMessage.FAIL_SEND_MESSAGE;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class FailSendMessageException extends ApplicationException {
    public FailSendMessageException() {
        super(NOT_FOUND.value(), FAIL_SEND_MESSAGE.getMessage());
    }
}
