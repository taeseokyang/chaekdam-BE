package eggis0.baram.domain.certification.exception;

import eggis0.baram.global.config.exception.ApplicationException;

import static eggis0.baram.domain.certification.presentation.constant.ResponseMessage.CERFIFI_NOT_FOUND;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class CertifiNotFoundException extends ApplicationException {
    public CertifiNotFoundException() {
        super(NOT_FOUND.value(), CERFIFI_NOT_FOUND.getMessage());
    }
}
