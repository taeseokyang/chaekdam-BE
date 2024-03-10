package eggis0.baram.global.config.security.exception;

import eggis0.baram.global.config.exception.ApplicationException;

import static eggis0.baram.domain.post.presentation.constant.ResponseMessage.POST_NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

public class InvalidTokenException extends ApplicationException {
    public InvalidTokenException() {
        super(UNAUTHORIZED.value(), POST_NOT_FOUND.getMessage());
    }
}
