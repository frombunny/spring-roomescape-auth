package roomescape.repository.fake;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.domain.User;
import roomescape.repository.UserRepository;

public class FakeUserRepository implements UserRepository {

    private final List<User> users = new CopyOnWriteArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);


    @Override
    public User save(User user) {
        Long id = idGenerator.getAndIncrement();
        User saved = User.restore(id,
                user.getName(),
                user.getLoginId(),
                user.getPassword(),
                user.getRole()
        );
        users.add(saved);
        return saved;
    }

    @Override
    public Optional<User> findById(Long id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }
}
