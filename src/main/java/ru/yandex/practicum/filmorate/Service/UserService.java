package ru.yandex.practicum.filmorate.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.Exceptions.HandlerNullPointException;
import ru.yandex.practicum.filmorate.Model.User;
import ru.yandex.practicum.filmorate.Storage.UserStorage;
import ru.yandex.practicum.filmorate.Validation.UserValidationService;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {
    private final UserStorage userStorage;
    private final UserValidationService userValidationService;

    public List<User> getAll() {
        return userStorage.getAll();
    }

    public User update(User user) {
        userValidationService.validUser(user);
        if (userStorage.getUserById(user.getId()).isPresent()) {
            log.info("Обновлены данные пользователя: " + user);
            return userStorage.update(user).get();
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
        if (userStorage.getUserById(id).isPresent()) {
            return userStorage.getUserById(id).get();
        } else {
            throw new HandlerNullPointException("Не найден пользователь с id: " + id);
        }
    }

    public void delete(int id) {
        userStorage.delete(id);
        log.info("Запрос на удаление пользователя с id: " + id);
    }

    public String addNewFriend(int hostId, int friendId) {
        String host = getUserByIdService(hostId).getName();
        String friend = getUserByIdService(friendId).getName();
        getUserByIdService(hostId).getFriendsList().add(friendId);
        getUserByIdService(friendId).getFriendsList().add(hostId);
        log.info(host + " и " + friend + " подружились");
        return host + " и " + friend + " подружились";
    }

    public String deleteFriend(int hostId, int friendId) {
        String host = getUserByIdService(hostId).getName();
        String friend = getUserByIdService(friendId).getName();
        getUserByIdService(hostId).getFriendsList().remove(friendId);
        getUserByIdService(friendId).getFriendsList().remove(hostId);
        log.info(host + " и " + friend + " больше не дружат");
        return host + " и " + friend + " больше не дружат";
    }

    public List<User> commonFriends(int hostId, int friendId) {
        ArrayList<User> answer = new ArrayList<>();
        for (int i: getUserByIdService(hostId).getFriendsList()) {
            if (getUserByIdService(friendId).getFriendsList().contains(i)) {
                answer.add(getUserByIdService(i));
            }
        }
        return answer;
    }

    public List<User> friendList(int id) {
        List<Integer> timeless = new ArrayList<>(getUserByIdService(id).getFriendsList());
        ArrayList<User> answer = new ArrayList<>();
        for (int i: timeless) {
            answer.add(getUserByIdService(i));
        }
        new ArrayList<>(getUserByIdService(id).getFriendsList());
        return answer;
    }
}
