package ru.yandex.practicum.filmorate.Service;

import com.google.common.collect.Sets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.Exceptions.HandlerNullPointException;
import ru.yandex.practicum.filmorate.Model.User;
import ru.yandex.practicum.filmorate.Storage.UserStorage;
import ru.yandex.practicum.filmorate.Validation.UserValidationService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
        Optional<User> timeless = userStorage.getUserById(id);
        if (timeless.isPresent()) {
            return timeless.get();
        } else {
            throw new HandlerNullPointException("Не найден пользователь с id: " + id);
        }
    }

    public void addNewFriend(int hostId, int friendId) {
        String host = getUserByIdService(hostId).getName();
        String friend = getUserByIdService(friendId).getName();
        getUserByIdService(hostId).getFriendsList().add(friendId);
        getUserByIdService(friendId).getFriendsList().add(hostId);
        log.info(host + " и " + friend + " подружились");
    }

    public void deleteFriend(int hostId, int friendId) {
        String host = getUserByIdService(hostId).getName();
        String friend = getUserByIdService(friendId).getName();
        getUserByIdService(hostId).getFriendsList().remove(friendId);
        getUserByIdService(friendId).getFriendsList().remove(hostId);
        log.info(host + " и " + friend + " больше не дружат");
    }

    public List<User> commonFriends(int hostId, int friendId) {
        Set<Integer> hostFriends = getUserByIdService(hostId).getFriendsList();
        Set<Integer> friendFriends = getUserByIdService(friendId).getFriendsList();
        Set<Integer> timeless = Sets.intersection(hostFriends, friendFriends);
        ArrayList<User> answer = new ArrayList<>();
        for(int i: timeless) {
            answer.add(userStorage.getUserById(i).get());
        }
        return answer;
    }

    public List<User> friendList(int id) {
        List<Integer> timeless = new ArrayList<>(getUserByIdService(id).getFriendsList());
        return timeless.stream()
                .map(this::getUserByIdService)
                .collect(Collectors.toList());
    }
}
