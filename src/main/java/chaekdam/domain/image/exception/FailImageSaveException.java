package chaekdam.domain.image.exception;

import chaekdam.global.config.exception.ApplicationException;

import static chaekdam.domain.image.presentation.constant.ResponseMessage.FAIL_IMAGE_SAVE;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class FailImageSaveException extends ApplicationException {
    public FailImageSaveException() {
        super(INTERNAL_SERVER_ERROR.value(), FAIL_IMAGE_SAVE.getMessage());
    }
}
