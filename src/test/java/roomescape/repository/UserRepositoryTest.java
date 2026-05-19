package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import roomescape.domain.User;
import roomescape.domain.fixture.UserFixture;

class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDataSource dataSource;

    @BeforeEach
    void setUp() {
        dataSource.clearId();
        dataSource.clearTable();
    }

    @Test
    void 사용자를_저장하고_조회할_수_있다() {
        // given
        User user = UserFixture.createDefaultUser();

        // when
        userRepository.save(user);

        // then
        assertThat(userRepository.findById(user.getId())).isPresent();
    }
}
