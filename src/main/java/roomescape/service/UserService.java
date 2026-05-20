package roomescape.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.web.dto.user.UserRequest;
import roomescape.domain.User;
import roomescape.repository.UserRepository;
import roomescape.web.dto.user.UserResponse;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserResponse register(UserRequest request) {
        User user = User.create(request.name(), request.loginId(), request.password(), request.role());
        return UserResponse.from(userRepository.save(user));
    }

}
