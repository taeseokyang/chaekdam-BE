package chaekdam.domain.user.exception;

import chaekdam.global.config.exception.ApplicationException;

import static chaekdam.domain.user.presentation.constant.ResponseMessage.INCORRECT_PASSWORD;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

public class IncorrectPasswordException extends ApplicationException {
    public IncorrectPasswordException() {
        super(UNAUTHORIZED.value(), INCORRECT_PASSWORD.getMessage());
    }
}