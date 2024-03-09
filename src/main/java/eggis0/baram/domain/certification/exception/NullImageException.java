package eggis0.baram.domain.certification.exception;

import eggis0.baram.global.config.exception.ApplicationException;

import static eggis0.baram.domain.certification.presentation.constant.ResponseMessage.NULL_IMAGE;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class NullImageException extends ApplicationException {
    public NullImageException() {
        super(NOT_FOUND.value(), NULL_IMAGE.getMessage());
    }
}
