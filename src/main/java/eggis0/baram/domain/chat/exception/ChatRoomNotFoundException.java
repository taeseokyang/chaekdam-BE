package eggis0.baram.domain.chat.exception;

import eggis0.baram.global.config.exception.ApplicationException;

import static eggis0.baram.domain.chat.presentation.constant.ResponseMessage.CHAT_ROOM_NOT_FOUND;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class ChatRoomNotFoundException extends ApplicationException {
    public ChatRoomNotFoundException() {
        super(NOT_FOUND.value(), CHAT_ROOM_NOT_FOUND.getMessage());
    }
}
