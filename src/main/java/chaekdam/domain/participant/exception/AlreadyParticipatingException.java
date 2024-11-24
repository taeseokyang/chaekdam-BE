package chaekdam.domain.participant.exception;

import chaekdam.global.config.exception.ApplicationException;

import static chaekdam.domain.participant.presentation.constant.ResponseMessage.ALREADY_PARTICIPATING;
import static org.springframework.http.HttpStatus.CONFLICT;

public class AlreadyParticipatingException extends ApplicationException {
    public AlreadyParticipatingException() {
        super(CONFLICT.value(), ALREADY_PARTICIPATING.getMessage());
    }
}