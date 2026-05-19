package roomescape.domain;

import lombok.Getter;

@Getter
public class User {
    private final Long id;
    private final String loginId;
    private final String password;
    private final String name;
    private final Role role;

    private User(Long id, String loginId, String password, String name, Role role) {
        this.id = id;
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.role = role;
    }

    public static User create(String name, String loginId, String password, Role role) {
        return new User(null, loginId, password, name, role);
    }
}
