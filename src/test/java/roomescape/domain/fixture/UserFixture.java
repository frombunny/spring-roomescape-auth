package roomescape.domain.fixture;

import roomescape.domain.Role;
import roomescape.domain.User;

public final class UserFixture {
    private UserFixture(){
    }

    public static User createDefaultUser(){
        return User.create("바니", "bunny", "1234", Role.ROLE_USER);
    }
}
