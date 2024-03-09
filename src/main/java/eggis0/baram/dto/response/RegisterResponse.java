package eggis0.baram.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
public class RegisterResponse {
    private boolean result;
    private String message;
    public RegisterResponse(boolean result, String message) {
        this.result = result;
        this.message = message;
    }
}
