package eggis0.baram.domain.user.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    ADMIN("ROLE_ADMIN"),
    MANAGER("ROLE_MANAGER"),
    USER("ROLE_USER");

    private final String role;

//    Role(String role) {
//        this.role = role;
//    }
//
//    public List<String> value() {
//        return Collections.unmodifiableList(Arrays.asList(this.role.split(",")));
//    }
}