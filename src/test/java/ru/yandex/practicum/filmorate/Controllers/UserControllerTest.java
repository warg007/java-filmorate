package ru.yandex.practicum.filmorate.Controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {
    UserController userController;

    User userOk1 = User.builder()
            .id(1)
            .email("test@mail.ru")
            .login("user1")
            .name("user1")
            .birthday(LocalDate.of(2000, 12, 12))
            .build();

    User userOk2 = User.builder()
            .id(2)
            .email("test@mail.ru")
            .login("user2")
            .name("user2")
            .birthday(LocalDate.of(2000, 12, 12))
            .build();

    User userWithWrongEmail = User.builder()
            .id(3)
            .email("test mail.ru")
            .login("user3")
            .name("user3")
            .birthday(LocalDate.of(2000, 12, 12))
            .build();

    User userWithWrongLogin = User.builder()
            .id(4)
            .email("test@mail.ru")
            .login("user  4")
            .name("user4")
            .birthday(LocalDate.of(2000, 12, 12))
            .build();

    User userWithoutName = User.builder()
            .id(5)
            .email("test@mail.ru")
            .login("user5")
            .birthday(LocalDate.of(2000, 12, 12))
            .build();

    User userWithWrongData = User.builder()
            .id(6)
            .email("test@mail.ru")
            .login("user6")
            .name("user6")
            .birthday(LocalDate.of(2222, 12, 12))
            .build();

    @BeforeEach
    void clearUserController() {
        userController = new UserController();
    }

    @Test
    void shouldGetAllUsersOrNot() {
        userController.addNewUser(userOk1);
        userController.addNewUser(userOk2);

        List<User> expected = new ArrayList<>();
        expected.add(userOk1);
        expected.add(userOk2);

        assertArrayEquals(expected.toArray(), userController.getAllUsers().toArray(),
                "Кол-во пользователей не совпадает");
    }

    @Test
    void shouldAddNewUserOrNot() {
        userController.addNewUser(userOk1);
        userController.addNewUser(userOk2);
        userController.addNewUser(userWithoutName);
        userController.addNewUser(userWithWrongData);
        userController.addNewUser(userWithWrongEmail);
        userController.addNewUser(userWithWrongLogin);

        List<User> expected = new ArrayList<>();
        expected.add(userOk1);
        expected.add(userOk2);
        expected.add(userWithoutName);

        assertArrayEquals(expected.toArray(), userController.getAllUsers().toArray(),
                "Кол-во пользователей не совпадает");
    }

    @Test
    void shouldUpdateOrNot() {
        userController.addNewUser(userOk1);
        userOk2.setId(1);
        userController.updateUser(userOk2);
        userWithoutName.setId(1);
        userController.updateUser(userWithoutName);
        userWithWrongData.setId(1);
        userController.updateUser(userWithWrongData);
        userWithWrongEmail.setId(1);
        userController.updateUser(userWithWrongEmail);
        userWithWrongLogin.setId(1);
        userController.updateUser(userWithWrongLogin);

        User expected = userWithoutName;
        List<User> timeless = userController.getAllUsers();
        User actual = timeless.get(0);

        assertEquals(expected, actual, "Данные пользователей не совпали");
    }
}