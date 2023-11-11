package com.example.subsub.domain;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "users")
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String userId;

    @Column(nullable = false)
    private String nickName;

    @Column(nullable = false)
    private String passWord;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Builder.Default
    private List<Authority> roles = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Subject> subjects;

    public void setRoles(List<Authority> role) {
        this.roles = role;
        role.forEach(o -> o.setUser(this));
    }
}