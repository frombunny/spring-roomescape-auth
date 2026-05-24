package roomescape.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import roomescape.domain.ReservationStatus;
import roomescape.domain.Role;

@Component
public class ReservationDataSource {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void clearTable() {
        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY FALSE");

        jdbcTemplate.execute("TRUNCATE TABLE reservation");
        jdbcTemplate.execute("TRUNCATE TABLE theme");
        jdbcTemplate.execute("TRUNCATE TABLE users");
        jdbcTemplate.execute("TRUNCATE TABLE reservation_time");

        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY TRUE");
    }

    public void clearId() {
        jdbcTemplate.execute("ALTER TABLE theme ALTER COLUMN id RESTART WITH 1");
        jdbcTemplate.execute("ALTER TABLE users ALTER COLUMN id RESTART WITH 1");
        jdbcTemplate.execute("ALTER TABLE reservation_time ALTER COLUMN id RESTART WITH 1");
        jdbcTemplate.execute("ALTER TABLE reservation ALTER COLUMN id RESTART WITH 1");
    }

    public void insertUser(String name, String loginId, String password, Role role) {
        jdbcTemplate.update("INSERT INTO users (name, login_id, password, role) VALUES (?,?,?,?)",
                name, loginId, password, role.name());
    }

    public void insertTheme(String name, String description, String thumbnailImageUrl) {
        jdbcTemplate.update("INSERT INTO theme (name, description, thumbnail_image_url) VALUES (?, ?, ?)",
                name, description, thumbnailImageUrl);
    }

    public void insertReservedReservation(Long userId, LocalDate date, Long themeId, Long timeId) {
        jdbcTemplate.update("INSERT INTO reservation (user_id, date, theme_id, time_id, status) VALUES (?, ?, ?, ?, ?)",
                userId, date, themeId, timeId, ReservationStatus.RESERVED.toString());
    }

    public void insertReservationTime(LocalTime reservationTime) {
        jdbcTemplate.update("INSERT INTO reservation_time (start_at) VALUES (?)", reservationTime);
    }

    public boolean hasReservationById(Long id) {
        String sql = "SELECT EXISTS (SELECT 1 FROM reservation WHERE id = ?)";
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, Boolean.class, id));
    }
}
