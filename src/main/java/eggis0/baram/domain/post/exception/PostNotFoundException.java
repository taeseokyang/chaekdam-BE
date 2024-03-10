package eggis0.baram.domain.post.exception;

import eggis0.baram.global.config.exception.ApplicationException;

import static eggis0.baram.domain.post.presentation.constant.ResponseMessage.POST_NOT_FOUND;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class PostNotFoundException extends ApplicationException {
    public PostNotFoundException() {
        super(NOT_FOUND.value(), POST_NOT_FOUND.getMessage());
    }
}
