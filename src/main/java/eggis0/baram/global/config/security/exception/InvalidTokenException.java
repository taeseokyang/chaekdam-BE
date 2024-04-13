package eggis0.baram.global.config.security.exception;

import eggis0.baram.global.config.exception.ApplicationException;

import static eggis0.baram.global.config.security.constant.ResponseMessage.INVALID_TOKEN;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

public class InvalidTokenException extends ApplicationException {
    public InvalidTokenException() {
        super(UNAUTHORIZED.value(), INVALID_TOKEN.getMessage());
    }
}
