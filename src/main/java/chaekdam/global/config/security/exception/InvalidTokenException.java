package chaekdam.global.config.security.exception;

import chaekdam.global.config.security.constant.ResponseMessage;
import chaekdam.global.config.exception.ApplicationException;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

public class InvalidTokenException extends ApplicationException {
    public InvalidTokenException() {
        super(UNAUTHORIZED.value(), ResponseMessage.INVALID_TOKEN.getMessage());
    }
}
