package roomescape.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import roomescape.domain.ReservationStatus;
import roomescape.domain.Role;

@Component
public class ThemeDataSource {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void clearTable() {
        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY FALSE");
        jdbcTemplate.execute("TRUNCATE TABLE reservation");
        jdbcTemplate.execute("TRUNCATE TABLE users");
        jdbcTemplate.execute("TRUNCATE TABLE reservation_time");
        jdbcTemplate.execute("TRUNCATE TABLE theme");
        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY TRUE");
    }

    public void clearId() {
        jdbcTemplate.execute("ALTER TABLE reservation_time ALTER COLUMN id RESTART WITH 1");
        jdbcTemplate.execute("ALTER TABLE theme ALTER COLUMN id RESTART WITH 1");
        jdbcTemplate.execute("ALTER TABLE users ALTER COLUMN id RESTART WITH 1");
        jdbcTemplate.execute("ALTER TABLE reservation ALTER COLUMN id RESTART WITH 1");
    }

    public void insertReservedReservation(Long userId, LocalDate date, Long themeId, Long timeId) {
        jdbcTemplate.update("INSERT INTO reservation (user_id, date, theme_id, time_id, status) VALUES (?, ?, ?, ?, ?)",
                userId, date,
                themeId, timeId, ReservationStatus.RESERVED.toString());
    }

    public void insertThemesByCount(int count) {
        for (int i = 0; i < count; i++) {
            jdbcTemplate.update("INSERT INTO theme (name, description, thumbnail_image_url) VALUES (?, ?, ?)", "테마" + i,
                    "설명" + i, "https://image.com/image" + i + ".png");
        }
    }

    public void insertTimeByStartToEndWithOneHourRotation(int startHour, int endHour) {
        String sql = "INSERT INTO reservation_time (start_at) VALUES (?)";
        for (int i = startHour; i <= endHour; i++) {
            jdbcTemplate.update(sql, LocalTime.of(i, 0));
        }
    }

    public void insertReservedReservationByTheme(Long userId, Long themeId, int reservationCount) {
        for (long timeId = 1L; timeId <= reservationCount; timeId++) {
            jdbcTemplate.update(
                    "INSERT INTO reservation (user_id, date, theme_id, time_id, status) VALUES (?, ?, ?, ?, ?)",
                    userId, LocalDate.now(), themeId, timeId, ReservationStatus.RESERVED.toString());
        }
    }

    public void insertUserByCount(int count) {
        for (int i = 1; i <= count; i++) {
            jdbcTemplate.update("INSERT INTO users (name, login_id, password, role) VALUES (?,?,?,?)",
                    "user" + i, "kaya" + i, "1234", Role.ROLE_USER.name());
        }
    }
}
