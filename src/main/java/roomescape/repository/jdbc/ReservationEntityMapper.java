package roomescape.repository.jdbc;

import org.springframework.jdbc.core.RowMapper;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationStatus;
import roomescape.domain.ReservationTime;
import roomescape.domain.Role;
import roomescape.domain.Theme;
import roomescape.domain.User;

public final class ReservationEntityMapper {

    public static final RowMapper<Reservation> RESERVATION_ROW_MAPPER = (rs, rowNum) -> {
        ReservationTime time = ReservationTime.restore(
                rs.getLong("time_id"),
                rs.getTime("time_start").toLocalTime(),
                rs.getBoolean("time_active")
        );
        Theme theme = Theme.restore(
                rs.getLong("theme_id"),
                rs.getString("theme_name"),
                rs.getString("description"),
                rs.getString("thumbnail_image_url"),
                rs.getBoolean("theme_active")
        );
        User user = User.restore(
                rs.getLong("user_id"),
                rs.getString("user_name"),
                rs.getString("login_id"),
                rs.getString("password"),
                Role.valueOf(rs.getString("role"))
        );
        return Reservation.restore(
                rs.getLong("res_id"),
                user,
                rs.getDate("res_date").toLocalDate(),
                theme,
                time,
                ReservationStatus.valueOf(rs.getString("res_status"))
        );
    };

    private ReservationEntityMapper() {
    }
}
