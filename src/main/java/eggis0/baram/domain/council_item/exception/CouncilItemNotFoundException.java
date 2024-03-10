package eggis0.baram.domain.council_item.exception;

import eggis0.baram.global.config.exception.ApplicationException;

import static eggis0.baram.domain.council_item.presentation.constant.ResponseMessage.COUNCIL_ITEM_NOT_FOUND;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class CouncilItemNotFoundException extends ApplicationException {
    public CouncilItemNotFoundException() {
        super(NOT_FOUND.value(), COUNCIL_ITEM_NOT_FOUND.getMessage());
    }
}
