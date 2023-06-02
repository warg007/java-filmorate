package ru.yandex.practicum.filmorate.Storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.Model.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Component
public class InMemoryUserStorage implements UserStorage{
    private final HashMap<Integer, User> userStorage = new HashMap<>();
    private int userId = 1;

    @Override
    public List<User> getAll() {
        return new ArrayList<>(userStorage.values());
    }

    @Override
    public Optional<User> update(User user) {
        if (userStorage.containsKey(user.getId())) {
            userStorage.put(user.getId(), user);
            return Optional.of(user);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public User addNewUser(User user) {
        user.setId(userId++);
        userStorage.put(user.getId(), user);
        return user;
    }

    @Override
    public void delete(Integer id) {
        userStorage.remove(id);
    }

    @Override
    public Optional<User> getUserById(int id) {
        if (userStorage.containsKey(id)) {
            return Optional.of(userStorage.get(id));
        } else {
            return Optional.empty();
        }
    }
}
