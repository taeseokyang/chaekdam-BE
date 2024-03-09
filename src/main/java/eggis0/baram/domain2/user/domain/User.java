package eggis0.baram.domain2.user.domain;
import eggis0.baram.domain2.post.domain.Post;
import eggis0.baram.domain2.user.domain.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "users")
@Builder
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(unique = true, nullable = false)
    private String userId;

    @Column(nullable = false)
    private String nickName;

    @Column
    private String imgPath = "default.png";

    @Column
    private String passWord;

//    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
//    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
    private List<Post> posts = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false)
    private boolean isCertification = false;

    @Column(nullable = false)
    private Integer borrowCount = 0;

    @Column(nullable = false)
    private Integer lendCount = 0;

    public void setRoles(Role roles) {
        this.role = role;
    }

    public void setCertification(Boolean isCertification) {
        this.isCertification = isCertification;
    }
}