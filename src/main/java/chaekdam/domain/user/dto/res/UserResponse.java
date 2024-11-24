package chaekdam.domain.user.dto.res;

import chaekdam.domain.user.domain.Role;
import chaekdam.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private Long id;
    private String userId;
    private String nickname;
    private String imgPath;
    private Role roles;
    private String token;
    private boolean isCertification;
    private Integer borrowCount;
    private Integer lendCount;

    public UserResponse(User user) {
        this.id = user.getId();
        this.userId = user.getUserId();
        this.nickname = user.getNickName();
        this.imgPath = user.getImgPath();
        this.roles = user.getRole();
    }
}