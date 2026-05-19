package roomescape.domain;

import lombok.Getter;
import roomescape.global.exception.ValidationException;

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
        validate(name, loginId, password, role);
        return new User(null, loginId, password, name, role);
    }

    public static User restore(Long id, String name, String loginId, String password, Role role) {
        return new User(id, loginId, password, name, role);
    }

    private static void validate(String name, String loginId, String password, Role role) {
        validateName(name);
        validateLoginId(loginId);
        validatePassword(password);
        validateRole(role);
    }

    private static void validateRole(Role role) {
        if (role == null) {
            throw new ValidationException("권한은 필수 값입니다.");
        }
    }

    private static void validatePassword(String password) {
        if (password == null || password.isBlank()) {
            throw new ValidationException("비밀번호는 필수 값입니다.");
        }
    }

    private static void validateLoginId(String loginId) {
        if (loginId == null || loginId.isBlank()) {
            throw new ValidationException("로그인 아이디는 필수 값입니다.");
        }
    }

    private static void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new ValidationException("이름은 필수 값입니다.");
        }
    }
}
