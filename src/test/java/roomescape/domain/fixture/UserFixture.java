package roomescape.domain.fixture;

import roomescape.domain.Role;
import roomescape.domain.User;

public final class UserFixture {
    private UserFixture() {
    }

    public static User createDefaultUser() {
        return User.create("바니", "bunny", "1234", Role.ROLE_USER);
    }

    public static User createDefaultUserWithId() {
        return User.restore(1L, "바니", "bunny", "1234", Role.ROLE_USER);
    }

    public static User createAnotherUser() {
        return User.create("네오", "neo", "1234", Role.ROLE_USER);
    }

    public static User createAnotherUserWithId() {
        return User.restore(2L, "네오", "neo", "1234", Role.ROLE_USER);
    }
}
