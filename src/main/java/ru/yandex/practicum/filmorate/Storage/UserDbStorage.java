package ru.yandex.practicum.filmorate.Storage;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.Model.User;
import ru.yandex.practicum.filmorate.RowMapper.UserMapper;

import java.util.List;
import java.util.Optional;

@Component
@Qualifier
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;
    private int id = 1;

    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> getAll() {
        return this.jdbcTemplate.query("select * from users", new UserMapper());
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
        return jdbcTemplate.query("select * from users where id = ?", new Object[]{id},
                new UserMapper()).stream().findAny();
    }
    }



