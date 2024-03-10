package eggis0.baram.domain.user.dto.req;

import eggis0.baram.domain.user.domain.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
    private Long id;

    private String userid;

    private String nickname;

    private String password;

    private Role role;
}
