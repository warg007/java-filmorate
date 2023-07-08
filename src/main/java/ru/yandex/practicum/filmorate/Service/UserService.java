package ru.yandex.practicum.filmorate.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.Exceptions.DataNotExistsException;
import ru.yandex.practicum.filmorate.Exceptions.HandlerNullPointException;
import ru.yandex.practicum.filmorate.Model.User;
import ru.yandex.practicum.filmorate.Storage.FriendDbStorage;
import ru.yandex.practicum.filmorate.Storage.UserDbStorage;
import ru.yandex.practicum.filmorate.Validation.UserValidationService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {
    private final UserDbStorage userStorage;
    private final UserValidationService userValidationService;
    private final FriendDbStorage friendStorage;

    public List<User> getAll() {
        return userStorage.getAll();
    }

    public User update(User user) {
        userValidationService.validUser(user);
        Optional<User> answer = userStorage.update(user);
        if (answer.isPresent()) {
            log.info("Обновлены данные пользователя: " + user);
            return answer.get();
        } else {
            throw new HandlerNullPointException("Пользователь с id " + user.getId() + " для обновления не найден");
        }
    }

    public User addNewUser(User user) {
        userValidationService.validUser(user);
        User timeless = userStorage.addNewUser(user);
        log.info("Новый пользователь сохранен: " + timeless);
        return timeless;
    }

    public User getUserByIdService(int id) {
        return userStorage.getUserById(id)
                        .orElseThrow(() -> new DataNotExistsException("Не найден пользователь с id: " + id));
    }

    public void addNewFriend(int hostId, int friendId) {
        String host = getUserByIdService(hostId).getName();
        String friend = getUserByIdService(friendId).getName();

        friendStorage.addFriend(hostId, friendId);
        log.info(host + " отправил запрос на дружбу " + friend);
    }

    public void deleteFriend(int hostId, int friendId) {
        String host = getUserByIdService(hostId).getName();
        String friend = getUserByIdService(friendId).getName();
        friendStorage.deleteFriend(hostId, friendId);
        log.info(host + " удалил из друзей " + friend);
    }

    public List<User> commonFriends(int hostId, int friendId) {
        List<User> hostFriends = friendList(hostId);
        List<User> friendsFriend = friendList(friendId);
        hostFriends.retainAll(friendsFriend);
        return hostFriends;
    }

    public List<User> friendList(int id) {
        return friendStorage.friendList(id);
    }
}
