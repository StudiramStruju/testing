package io.test.postgres.dao;

import io.test.postgres.domain.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class UserDao {

    public static final UserMapper USER_MAPPER = new UserMapper();

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public UserDao(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<User> list() {
        return this.jdbcTemplate
                .query("select * from public.user", USER_MAPPER);
    }

    public User getById(final Integer id) {
        return this.jdbcTemplate
                .queryForObject("select * from public.user where id = :id", Map.of("id", id), USER_MAPPER);
    }

    public int create(final User user) {
        return this.jdbcTemplate.update(
                "insert into public.user(full_name, email, password) values(:fullName, :email, :password)",
                Map.of(
                        "fullName", user.getFullName(),
                        "email", user.getEmail(),
                        "password", user.getPassword()
                ));
    }

    public int update(final User user) {
        Objects.requireNonNull(user.getId(), "User id can't be null when updating user!");
        return this.jdbcTemplate.update(
                "update public.user set full_name = :fullName, password = :password where id = :id",
                Map.of(
                        "id", user.getId(),
                        "fullName", user.getFullName(),
                        "password", user.getPassword()
                ));
    }

    public static class UserMapper implements RowMapper<User> {
        @Override
        public User mapRow(final ResultSet rs, final int i) throws SQLException {
            return new User(
                    rs.getInt("id"),
                    rs.getString("full_name"),
                    rs.getString("email"),
                    rs.getString("password")
            );
        }
    }
}
