package eggis0.baram.domain.user.exception;

import eggis0.baram.global.config.exception.ApplicationException;

import static eggis0.baram.domain.user.presentation.constant.ResponseMessage.USER_NOT_FOUND;
import static org.springframework.http.HttpStatus.CONFLICT;

public class DuplicateUserIdException extends ApplicationException {
    public DuplicateUserIdException() {
        super(CONFLICT.value(), USER_NOT_FOUND.getMessage());
    }
}