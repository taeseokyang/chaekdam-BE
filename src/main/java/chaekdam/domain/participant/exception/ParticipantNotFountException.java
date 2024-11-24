package chaekdam.domain.participant.exception;

import chaekdam.global.config.exception.ApplicationException;

import static chaekdam.domain.participant.presentation.constant.ResponseMessage.PARTICIPANT_NOT_FOUND;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class ParticipantNotFountException extends ApplicationException {
    public ParticipantNotFountException() {
        super(NOT_FOUND.value(), PARTICIPANT_NOT_FOUND.getMessage());
    }
}