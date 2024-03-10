package eggis0.baram.domain.council.exception;

import eggis0.baram.global.config.exception.ApplicationException;

import static eggis0.baram.domain.council.presentation.constant.ResponseMessage.COUNCIL_NOT_FOUND;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class CouncilNotFoundException extends ApplicationException {
    public CouncilNotFoundException() {
        super(NOT_FOUND.value(), COUNCIL_NOT_FOUND.getMessage());
    }
}
