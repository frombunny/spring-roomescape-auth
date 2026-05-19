package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import roomescape.domain.Role;
import roomescape.repository.UserRepository;
import roomescape.repository.fake.FakeUserRepository;
import roomescape.web.dto.theme.ThemeResponse;
import roomescape.web.dto.user.UserRequest;
import roomescape.web.dto.user.UserResponse;

class UserServiceTest {

    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    void setUp(){
        this.userRepository = new FakeUserRepository();
        this.userService = new UserService(userRepository);
    }

    @Test
    void 새로운_사용자를_정상적으로_가입시킨다(){
        // given
        UserRequest request = new UserRequest("바니", "bunny", "1234", Role.ROLE_USER);

        // when
        UserResponse response = userService.save(request);

        // then
        assertThat(response).extracting(UserResponse::id, UserResponse::name, UserResponse::loginId)
                .containsExactly(1L, "바니", "bunny");
    }
}
