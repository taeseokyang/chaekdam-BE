package eggis0.baram.domain2.user.dto.req;

import eggis0.baram.domain2.user.domain.Role;
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
