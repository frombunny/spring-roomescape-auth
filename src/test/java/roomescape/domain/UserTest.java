package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import roomescape.global.exception.ValidationException;

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

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "  "})
    void 사용자_이름이_빈_값이면_예외가_발생한다(String invalidName) {
        // given
        String loginId = "bunny";
        String password = "1234";
        Role role = Role.ROLE_USER;

        // when & then
        assertThatThrownBy(() -> User.create(invalidName, loginId, password, role))
                .isInstanceOf(ValidationException.class);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "  "})
    void 사용자_로그인_아이디가_빈_값이면_예외가_발생한다(String invalidLoginId) {
        // given
        String name = "네오";
        String password = "1234";
        Role role = Role.ROLE_USER;

        // when & then
        assertThatThrownBy(() -> User.create(name, invalidLoginId, password, role))
                .isInstanceOf(ValidationException.class);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "  "})
    void 사용자_비밀번호가_빈_값이면_예외가_발생한다(String invalidPassword) {
        // given
        String name = "네오";
        String loginId = "neo";
        Role role = Role.ROLE_USER;

        // when & then
        assertThatThrownBy(() -> User.create(name, loginId, invalidPassword, role))
                .isInstanceOf(ValidationException.class);
    }

    @Test
    void 사용자_권한_정보가_없다면_예외가_발생한다() {
        // given
        String name = "네오";
        String loginId = "neo";
        String password = "1234";

        // when & then
        assertThatThrownBy(() -> User.create(name, loginId, password, null))
                .isInstanceOf(ValidationException.class);
    }
}
