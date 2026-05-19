package roomescape.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserDataSource {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void clearTable(){
        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY FALSE");
        jdbcTemplate.execute("TRUNCATE TABLE USER");
        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY TRUE");
    }

    public void clearId() {
        jdbcTemplate.execute("ALTER TABLE user ALTER COLUMN id RESTART WITH 1");
    }

    public void insertUser(){

    }

}
