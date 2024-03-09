package eggis0.baram.domain2.oauth.dto.req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoSignUpRequest {
    private String email;
    private String nickname;
}
