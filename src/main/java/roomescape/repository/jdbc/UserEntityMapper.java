package roomescape.repository.jdbc;

import org.springframework.jdbc.core.RowMapper;
import roomescape.domain.Role;
import roomescape.domain.User;

public final class UserEntityMapper {
    public static final RowMapper<User> USER_MAPPER = (rs, rowNum) -> (User.restore(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getString("login_id"),
            rs.getString("password"),
            Role.valueOf(rs.getString("role"))
    ));

    private UserEntityMapper() {
    }

}
