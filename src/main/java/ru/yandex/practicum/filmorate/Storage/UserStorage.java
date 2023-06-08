package ru.yandex.practicum.filmorate.Storage;

import ru.yandex.practicum.filmorate.Model.User;
import java.util.List;
import java.util.Optional;

public interface UserStorage {
    List<User> getAll();

    Optional<User> update(User user);

    User addNewUser(User user);

    Optional<User> getUserById(int id);
}
