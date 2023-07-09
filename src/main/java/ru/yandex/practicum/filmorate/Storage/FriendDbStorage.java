package ru.yandex.practicum.filmorate.Storage;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.Model.User;
import ru.yandex.practicum.filmorate.RowMapper.UserMapper;

import java.util.ArrayList;
import java.util.List;

@Component
public class FriendDbStorage {
    private final JdbcTemplate jdbcTemplate;

    public FriendDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addFriend(int hostId, int friendId) {
        jdbcTemplate.update("insert into friends (id_host, id_friend) values (?, ?)",hostId, friendId);
    }

    public void deleteFriend(int hostId, int friendId) {
        jdbcTemplate.update("delete from friends where id_host = ? and id_friend = ?", hostId, friendId);
    }

    public List<User> friendList(int id) {
        String link = "select id_friend from friends where id_host = " + id;
        List<Integer> idFriend = jdbcTemplate.query(link, (resultSet, rowNum) -> resultSet.getInt("id_friend"));
        List<User> allFriendInOneList = new ArrayList<>();
        for (int i: idFriend) {
            allFriendInOneList.add(jdbcTemplate.queryForObject("select * from users where id = ?", new Object[]{i}, new UserMapper()));
        }
        return allFriendInOneList;
    }
}
