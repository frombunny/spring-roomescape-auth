package roomescape.web.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import roomescape.domain.Role;

public record UserRequest(
        @NotBlank(message = "이름은 필수 값입니다.")
        String name,

        @NotBlank(message = "로그인 아이디는 필수 값입니다.")
        String loginId,

        @NotBlank(message = "비밀번호는 필수 값입니다.")
        String password,

        @NotNull(message = "권한은 필수 값입니다.")
        Role role
) {
}
