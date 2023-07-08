package ru.yandex.practicum.filmorate.Storage;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
public class UserDbStorageTest {
    private final UserDbStorage userStorage;

    User user1;
    User user2;
    User user3;

    @BeforeEach
    private void init() {
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
    public void testGetAll() {
        userStorage.addNewUser(user2);

        List<User> allUsers = userStorage.getAll();

        assertEquals(2, allUsers.size());
    }

    @Test
    public void testUpdate() {
        userStorage.update(user3);

        List<User> allUsersUpdated = userStorage.getAll();
        Optional<User> newUser1 = userStorage.getUserById(1);

        assertEquals(2, allUsersUpdated.size());
        assertThat(newUser1)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user).hasFieldOrPropertyWithValue("id", 1)
                );
    }
}
