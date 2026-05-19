package roomescape.repository;

import java.util.Optional;
import roomescape.domain.User;

public interface UserRepository {
    User save(User user);

    Optional<User> findById(Long id);
}
