package ru.yandex.practicum.filmorate.Service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.Model.User;
import ru.yandex.practicum.filmorate.Storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.Validation.UserValidationService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

class UserServiceTest {
    UserService userService;
    InMemoryUserStorage userStorage;
    UserValidationService validationService;

    @BeforeEach
    void init() {
        userStorage = new InMemoryUserStorage();
        validationService = new UserValidationService();
        userService = new UserService(userStorage, validationService);
    }

    User user1 = User.builder()
            .email("test1@mail.ru")
            .login("test1")
            .id(1)
            .birthday(LocalDate.of(2001, 12, 12))
            .name("test1")
            .build();

    User user2 = User.builder()
            .email("test2@mail.ru")
            .login("test2")
            .id(2)
            .birthday(LocalDate.of(2002, 12, 12))
            .name("test2")
            .build();

    User user3 = User.builder()
            .email("test3@mail.ru")
            .login("test3")
            .id(1)
            .birthday(LocalDate.of(2003, 12, 12))
            .name("test3")
            .build();

    User user4 = User.builder()
            .email("test4@mail.ru")
            .login("test4")
            .id(3)
            .birthday(LocalDate.of(2002, 12, 12))
            .name("test4")
            .build();

    @Test
    void getAll() {
        userService.addNewUser(user1);
        userService.addNewUser(user2);


        List<User> actual = new ArrayList<>();
        actual.add(user1);
        actual.add(user2);


        List<User> expected = userService.getAll();

        Assertions.assertArrayEquals(actual.toArray(), expected.toArray());
    }

    @Test
    void update() {
        userService.addNewUser(user1);
        userService.update(user3);

        User expected = userService.getUserByIdService(1);

        Assertions.assertEquals(user3, expected);
    }

    @Test
    void addNewUserAndGetUserByIdService() {
        userService.addNewUser(user1);

        User actual = userService.getUserByIdService(1);
        User expected = user1;

        Assertions.assertEquals(actual, expected);
    }

    @Test
    void addNewFriend() {
        userService.addNewUser(user1);
        userService.addNewUser(user2);
        userService.addNewFriend(user1.getId(), user2.getId());

        Assertions.assertTrue(user2.getFriendsList().contains(user1.getId()));
        Assertions.assertTrue(user1.getFriendsList().contains(user2.getId()));
    }

    @Test
    void deleteFriend() {
        userService.addNewUser(user1);
        userService.addNewUser(user2);
        userService.addNewFriend(user1.getId(), user2.getId());
        userService.deleteFriend(user1.getId(), user2.getId());

        Assertions.assertTrue(user2.getFriendsList().isEmpty());
        Assertions.assertTrue(user1.getFriendsList().isEmpty());
    }

    @Test
    void commonFriends() {
        userService.addNewUser(user1);
        userService.addNewUser(user2);
        userService.addNewUser(user4);
        userService.addNewFriend(user1.getId(), user2.getId());
        userService.addNewFriend(user1.getId(), user4.getId());
        userService.addNewFriend(user2.getId(), user4.getId());

        List<User> expected = userService.commonFriends(user1.getId(), user2.getId());
        List<User> actual = new ArrayList<>();
        actual.add(user4);

        Assertions.assertArrayEquals(actual.toArray(), expected.toArray());
    }

    @Test
    void friendList() {
        userService.addNewUser(user1);
        userService.addNewUser(user2);
        userService.addNewUser(user4);
        userService.addNewFriend(user1.getId(), user2.getId());
        userService.addNewFriend(user1.getId(), user4.getId());
        userService.addNewFriend(user2.getId(), user4.getId());

        List<User> expected = userService.friendList(user1.getId());
        List<User> actual = new ArrayList<>();
        actual.add(user2);
        actual.add(user4);

        Assertions.assertArrayEquals(actual.toArray(), expected.toArray());
    }
}