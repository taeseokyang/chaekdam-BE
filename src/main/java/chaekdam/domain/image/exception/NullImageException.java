package chaekdam.domain.image.exception;

import chaekdam.global.config.exception.ApplicationException;

import static chaekdam.domain.image.presentation.constant.ResponseMessage.NULL_IMAGE;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class NullImageException extends ApplicationException {
    public NullImageException() {
        super(NOT_FOUND.value(), NULL_IMAGE.getMessage());
    }
}
