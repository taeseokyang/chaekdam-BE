package chaekdam.domain.user.dto.req;

import chaekdam.domain.user.domain.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
    private String id;

    private String nickname;

    private String password;
}
