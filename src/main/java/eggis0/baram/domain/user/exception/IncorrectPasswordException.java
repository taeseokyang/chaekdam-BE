package eggis0.baram.domain.user.exception;

import eggis0.baram.global.config.exception.ApplicationException;

import static eggis0.baram.domain.user.presentation.constant.ResponseMessage.INCORRECT_PASSWORD;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

public class IncorrectPasswordException extends ApplicationException {
    public IncorrectPasswordException() {
        super(UNAUTHORIZED.value(), INCORRECT_PASSWORD.getMessage());
    }
}