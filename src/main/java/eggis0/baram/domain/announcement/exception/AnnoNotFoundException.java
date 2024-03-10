package eggis0.baram.domain.announcement.exception;

import eggis0.baram.global.config.exception.ApplicationException;

import static eggis0.baram.domain.announcement.presentation.constant.ResponseMessage.ANNO_NOT_FOUND;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class AnnoNotFoundException extends ApplicationException {
    public AnnoNotFoundException() {
        super(NOT_FOUND.value(), ANNO_NOT_FOUND.getMessage());
    }
}