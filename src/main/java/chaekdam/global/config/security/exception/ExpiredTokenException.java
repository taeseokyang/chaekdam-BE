package chaekdam.global.config.security.exception;

import chaekdam.global.config.security.constant.ResponseMessage;
import chaekdam.global.config.exception.ApplicationException;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

public class ExpiredTokenException extends ApplicationException {
    public ExpiredTokenException() {
        super(UNAUTHORIZED.value(), ResponseMessage.EXPIRED_TOKEN.getMessage());
    }
}
