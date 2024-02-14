package com.example.subsub.domain;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    private Role roles;

    @Column(nullable = false)
    private boolean isCertification = false;

    @Column(nullable = false)
    private Integer borrowCount = 0;

    @Column(nullable = false)
    private Integer lendCount = 0;

    public void setRoles(Role roles) {
        this.roles = roles;
    }

    public void setCertification(Boolean isCertification) {
        this.isCertification = isCertification;
    }
}