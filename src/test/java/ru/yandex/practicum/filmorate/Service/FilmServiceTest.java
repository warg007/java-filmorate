package ru.yandex.practicum.filmorate.Service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.Model.Film;
import ru.yandex.practicum.filmorate.Model.User;
import ru.yandex.practicum.filmorate.Storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.Storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.Validation.UserValidationService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class FilmServiceTest {
    InMemoryFilmStorage filmStorage;
    InMemoryUserStorage userStorage;
    UserValidationService validationService;
    UserService userService;
    FilmService filmService;

    @BeforeEach
    void init() {
        filmStorage = new InMemoryFilmStorage();
        userStorage = new InMemoryUserStorage();
        validationService = new UserValidationService();
        userService = new UserService(userStorage, validationService);
        filmService = new FilmService(filmStorage, userService);
    }

    Film film1 = Film.builder()
            .name("film1")
            .id(1)
            .description("film1")
            .duration(10)
            .releaseDate(LocalDate.of(2000,12,12))
            .build();

    Film film2 = Film.builder()
            .name("film2")
            .id(2)
            .description("film2")
            .duration(10)
            .releaseDate(LocalDate.of(2002,12,12))
            .build();

    Film film3 = Film.builder()
            .name("film3")
            .id(1)
            .description("film3")
            .duration(10)
            .releaseDate(LocalDate.of(2003,12,12))
            .build();

    User user1 = User.builder()
            .email("test1@mail.ru")
            .login("test1")
            .birthday(LocalDate.of(2000, 12, 12))
            .name("test1")
            .build();

    User user2 = User.builder()
            .email("test2@mail.ru")
            .login("test2")
            .birthday(LocalDate.of(2000, 12, 12))
            .name("test2")
            .build();

    @Test
    void getAll() {
        filmService.addNew(film1);
        filmService.addNew(film2);
        filmService.addNew(film3);

        Object[] expected = filmService.getAll().toArray();
        Object[] actual = new Object[]{film1, film2, film3};

        Assertions.assertArrayEquals(actual, expected);
    }

    @Test
    void update() {
        filmService.addNew(film1);
        filmService.addNew(film2);
        filmService.update(film3);

        Film expected = filmService.getFilmByIdService(film3.getId());

        Assertions.assertEquals(film3, expected);
    }

    @Test
    void addNewAndGetFilmByIdService() {
        filmService.addNew(film1);
        filmService.addNew(film2);

        Film excepted = filmService.getFilmByIdService(film1.getId());

        Assertions.assertEquals(film1, excepted);
    }

    @Test
    void likedFilm() {
        userService.addNewUser(user1);
        filmService.addNew(film1);
        filmService.likedFilm(user1.getId(), film1.getId());

        int expected = film1.getLikesList().size();
        int actual = 1;

        Assertions.assertEquals(actual, expected);
    }

    @Test
    void deleteLike() {
        userService.addNewUser(user1);
        filmService.addNew(film1);
        filmService.likedFilm(user1.getId(), film1.getId());
        filmService.deleteLike(user1.getId(), film1.getId());

        int expected = film1.getLikesList().size();
        int actual = 0;

        Assertions.assertEquals(actual, expected);
    }

    @Test
    void topLiked() {
        Optional<Integer> count = Optional.of(1);
        userService.addNewUser(user1);
        userService.addNewUser(user2);
        filmService.addNew(film1);
        filmService.addNew(film2);
        filmService.likedFilm(user1.getId(), film1.getId());
        filmService.likedFilm(user1.getId(), film2.getId());
        filmService.likedFilm(user2.getId(), film2.getId());

        List<Film> expected = filmService.topLiked(count);
        List<Film> actual = new ArrayList<>();
        actual.add(film2);

        Assertions.assertArrayEquals(actual.toArray(), expected.toArray());
    }
}