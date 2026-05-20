package roomescape.web.dto.user;

import roomescape.domain.User;

public record UserResponse(
        Long id,
        String loginId,
        String name
) {
    public static UserResponse from(User user) {
        return new UserResponse(user.getId(), user.getLoginId(), user.getName());
    }
}
