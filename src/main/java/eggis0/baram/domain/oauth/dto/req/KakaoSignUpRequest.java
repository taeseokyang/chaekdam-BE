package eggis0.baram.domain.oauth.dto.req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoSignUpRequest {
    private String code;
    private String nickname;
}