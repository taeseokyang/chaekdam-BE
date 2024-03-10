package eggis0.baram.domain.user.dto.res;

import eggis0.baram.domain.user.domain.Role;
import eggis0.baram.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private String id;
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
        this.isCertification = user.isCertification();
        this.borrowCount = user.getBorrowCount();
        this.lendCount = user.getLendCount();
    }
}