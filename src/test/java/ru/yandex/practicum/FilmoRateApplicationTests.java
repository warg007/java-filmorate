//package ru.yandex.practicum;
//
//import lombok.RequiredArgsConstructor;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.jdbc.core.JdbcTemplate;
//import ru.yandex.practicum.filmorate.Model.User;
//import ru.yandex.practicum.filmorate.Storage.UserDbStorage;
//
//import java.util.Optional;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//
//@SpringBootTest(classes = UserDbStorage.class)
//@AutoConfigureTestDatabase
//@RequiredArgsConstructor(onConstructor_ = @Autowired)
//public class FilmoRateApplicationTests {
//    private final JdbcTemplate jdbcTemplate = new JdbcTemplate();
//    private final UserDbStorage userStorage = new UserDbStorage(jdbcTemplate);
//
//    @Test
//    public void testFindUserById() {
//
//        Optional<User> userOptional = userStorage.getUserById(1);
//
//        assertThat(userOptional)
//                .isPresent()
//                .hasValueSatisfying(user ->
//                        assertThat(user).hasFieldOrPropertyWithValue("id", 1)
//                );
//    }
//}
