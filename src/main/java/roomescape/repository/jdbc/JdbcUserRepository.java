package roomescape.repository.jdbc;

import static roomescape.repository.jdbc.UserEntityMapper.USER_MAPPER;

import java.sql.PreparedStatement;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.User;
import roomescape.repository.UserRepository;

@Repository
@RequiredArgsConstructor
public class JdbcUserRepository implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public User save(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO users (login_id,name,password,role) VALUES (?,?,?,?)";

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, user.getLoginId());
            ps.setString(2, user.getName());
            ps.setString(3, user.getPassword());
            ps.setString(4, String.valueOf(user.getRole()));
            return ps;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();
        return User.restore(id,
                user.getName(),
                user.getLoginId(),
                user.getPassword(),
                user.getRole());
    }

    @Override
    public Optional<User> findById(Long id) {
        try {
            String sql = "SELECT * FROM users WHERE id = ?";
            User user =  jdbcTemplate.queryForObject(sql, USER_MAPPER, id);
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
