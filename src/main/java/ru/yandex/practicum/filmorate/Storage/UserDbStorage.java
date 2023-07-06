package ru.yandex.practicum.filmorate.Storage;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.Model.User;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@Qualifier

public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;
    private int id = 1;
    private UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> getAll() {
        return this.jdbcTemplate.query("select id, email, login, name, birthday from users",
                (resultSet, rowNum) -> {
            User user = new User();
            user.setId(resultSet.getInt("id"));
            user.setEmail(resultSet.getString("email"));
            user.setLogin(resultSet.getString("login"));
            user.setName(resultSet.getString("name"));
            user.setBirthday(resultSet.getDate("birthday").toLocalDate());
            return user;
                });
    }

    @Override
    public Optional<User> update(User user) {
        if (getUserById(user.getId()).isPresent()) {
            jdbcTemplate.update("update users set email = ?, login = ?, name = ?, birthday = ? where id =?",
                    user.getEmail(), user.getLogin(), user.getName(), user.getBirthday(), user.getId());
            return Optional.of(user);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public User addNewUser(User user) {
        user.setId(id++);
        jdbcTemplate.update("insert into users (id, email, login, name, birthday) values (?, ?, ?, ?, ?)",
                user.getId(), user.getEmail(), user.getLogin(), user.getName(), user.getBirthday());
        return user;
    }

    @Override
    public Optional<User> getUserById(int id) {
        SqlRowSet userRows = jdbcTemplate.queryForRowSet(
                "select * from users where id = ?", id
        );
        if (userRows.next()) {
            User user = new User(
                    userRows.getInt("id"),
                    userRows.getString("email"),
                    userRows.getString("login"),
                    userRows.getString("name"),
                    Objects.requireNonNull(userRows.getDate("birthday")).toLocalDate()
            );
            return Optional.of(user);
        }
        return Optional.empty();
    }
}
