package eggis0.baram.domain.image.exception;

import eggis0.baram.global.config.exception.ApplicationException;

import static eggis0.baram.domain.certification.presentation.constant.ResponseMessage.NULL_IMAGE;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class FailImageSaveException extends ApplicationException {
    public FailImageSaveException() {
        super(INTERNAL_SERVER_ERROR.value(), NULL_IMAGE.getMessage());
    }
}
