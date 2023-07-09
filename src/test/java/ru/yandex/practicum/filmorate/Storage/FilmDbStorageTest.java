package ru.yandex.practicum.filmorate.Storage;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.Model.Film;
import ru.yandex.practicum.filmorate.Model.Genre;
import ru.yandex.practicum.filmorate.Model.Mpa;
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
class FilmDbStorageTest {

    private final FilmDbStorage filmDbStorage;
    private final UserDbStorage userDbStorage;
     Film film1;
     Film film2;
     Film film3;
     User user;


    @BeforeEach
    public void init() {
        film1 = new Film(1,
                "name1",
                "des1",
                LocalDate.of(2000, 12, 12),
                10,
                1,
                new Mpa(1, "G"),
                List.of(new Genre(1, "Комедия")));

        film2 = new Film(2,
                "name2",
                "des2",
                LocalDate.of(2002, 12, 12),
                10,
                1,
                new Mpa(1, "G"),
                List.of(new Genre(1, "Комедия")));

        film3 = new Film(1,
                "name3",
                "des3",
                LocalDate.of(2003, 12, 12),
                10,
                1,
                new Mpa(1, "G"),
                List.of(new Genre(1, "Комедия")));

        user = new User(1,
                "test@mail.ru",
                "user",
                "name",
                LocalDate.of(1900, 12, 12));
    }

    @Test
    @Order(1)
    void testGetAll() {
        filmDbStorage.addNew(film1);
        List<Film> allFilms = filmDbStorage.getAll();

        assertEquals(1, allFilms.size());
    }

    @Test
    @Order(2)
    void testUpdate() {
        filmDbStorage.update(film3);

        Optional<Film> film = filmDbStorage.getFilmById(1);

        assertThat(film)
                .isPresent()
                .hasValueSatisfying(film4 ->
                        assertThat(film4).hasFieldOrPropertyWithValue("id", 1)
                                .hasFieldOrPropertyWithValue("name", "name3")
                );
    }

    @Test
    @Order(3)
    void testGetFilmById() {
        filmDbStorage.addNew(film2);

        Optional<Film> film = filmDbStorage.getFilmById(2);

        assertThat(film)
                .isPresent()
                .hasValueSatisfying(film4 ->
                        assertThat(film4).hasFieldOrPropertyWithValue("id", 2)
                                .hasFieldOrPropertyWithValue("name", "name2")
                );

    }

    @Test
    @Order(4)
    void testLikedFilm() {
        userDbStorage.addNewUser(user);
        filmDbStorage.likedFilm(1, 2);

        Optional<Film> likedFilm = filmDbStorage.getFilmById(2);

        assertThat(likedFilm)
                .isPresent()
                .hasValueSatisfying(film4 ->
                        assertThat(film4).hasFieldOrPropertyWithValue("id", 2)
                                .hasFieldOrPropertyWithValue("rate", 2));
    }

    @Test
    @Order(5)
    void testDeleteLike() {
        filmDbStorage.deleteLike(1, 2);

        Optional<Film> likedFilm = filmDbStorage.getFilmById(2);

        assertThat(likedFilm)
                .isPresent()
                .hasValueSatisfying(film4 ->
                        assertThat(film4).hasFieldOrPropertyWithValue("id", 2)
                                .hasFieldOrPropertyWithValue("rate", 1));
    }

    @Test
    @Order(6)
    void testGetSortedByLikes() {
        filmDbStorage.likedFilm(1, 2);
        Optional<Integer> count = Optional.of(10);

        List<Film> popularFilms = filmDbStorage.getSortedByLikes(count);
        Film mostPopularFilm = popularFilms.get(0);
        Film lessPopularFilm = popularFilms.get(1);

        assertThat(mostPopularFilm)
                .hasFieldOrPropertyWithValue("id", 2)
                .hasFieldOrPropertyWithValue("name", "name2")
                .hasFieldOrPropertyWithValue("rate", 2);

        assertThat(lessPopularFilm)
                .hasFieldOrPropertyWithValue("id", 1)
                .hasFieldOrPropertyWithValue("name", "name3")
                .hasFieldOrPropertyWithValue("rate", 1);
    }
}