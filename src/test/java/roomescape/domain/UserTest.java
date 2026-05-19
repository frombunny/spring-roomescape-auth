package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class UserTest {

    @Test
    void 정상적인_사용자_정보를_생성한다() {
        // given
        String name = "바니";
        String loginId = "bunny";
        String password = "1234";
        Role role = Role.ROLE_USER;

        // when
        User user = User.create(name, loginId, password, role);

        // then
        assertThat(user).extracting(User::getName, User::getLoginId, User::getPassword, User::getRole)
                .containsExactly(name, loginId, password, role);
    }
}
