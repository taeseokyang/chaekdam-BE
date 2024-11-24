package chaekdam.domain.user.exception;

import chaekdam.global.config.exception.ApplicationException;

import static chaekdam.domain.user.presentation.constant.ResponseMessage.USER_NOT_FOUND;
import static org.springframework.http.HttpStatus.CONFLICT;

public class DuplicateUserIdException extends ApplicationException {
    public DuplicateUserIdException() {
        super(CONFLICT.value(), USER_NOT_FOUND.getMessage());
    }
}