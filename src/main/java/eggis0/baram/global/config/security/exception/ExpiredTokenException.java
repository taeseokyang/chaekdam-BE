package eggis0.baram.global.config.security.exception;

import eggis0.baram.global.config.exception.ApplicationException;

import static eggis0.baram.global.config.security.constant.ResponseMessage.EXPIRED_TOKEN;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

public class ExpiredTokenException extends ApplicationException {
    public ExpiredTokenException() {
        super(UNAUTHORIZED.value(), EXPIRED_TOKEN.getMessage());
    }
}
