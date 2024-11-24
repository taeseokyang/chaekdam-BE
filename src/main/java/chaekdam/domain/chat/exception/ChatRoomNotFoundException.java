package chaekdam.domain.chat.exception;

import chaekdam.domain.chat.presentation.constant.ResponseMessage;
import chaekdam.global.config.exception.ApplicationException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class ChatRoomNotFoundException extends ApplicationException {
    public ChatRoomNotFoundException() {
        super(NOT_FOUND.value(), ResponseMessage.CHAT_ROOM_NOT_FOUND.getMessage());
    }
}
