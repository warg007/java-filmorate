package ru.yandex.practicum.filmorate.Storage;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.Model.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserDbStorageTest {
    private final UserDbStorage userStorage;

    static User user1;
    static User user2;
    static User user3;

    @BeforeAll
    private static void init() {
        user1 = new User(1,
                "test1@mail.ru",
                "login1",
                "name1",
                LocalDate.of(2000, 12, 12));

        user2 = new User(2,
                "test2@mail.ru",
                "login2",
                "name2",
                LocalDate.of(2002, 12, 12));

        user3 = new User(1,
                "test3@mail.ru",
                "login3",
                "name3",
                LocalDate.of(2003, 12, 12));

    }

    @Test
    @Order(1)
    public void testFindUserById() {
        userStorage.addNewUser(user1);
        Optional<User> userOptional = userStorage.getUserById(1);

        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user).hasFieldOrPropertyWithValue("id", 1)
                );
    }

    @Test
    @Order(2)
    public void testGetAll() {
        userStorage.addNewUser(user2);

        List<User> allUsers = userStorage.getAll();

        assertEquals(3, allUsers.size());
    }

    @Test
    @Order(3)
    public void testUpdate() {
        userStorage.update(user3);

        List<User> allUsersUpdated = userStorage.getAll();
        Optional<User> newUser1 = userStorage.getUserById(1);

        assertEquals(3, allUsersUpdated.size());
        assertThat(newUser1)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user).hasFieldOrPropertyWithValue("id", 1)
                );
    }
}
