package ru.yandex.practicum.filmorate.Storage;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.Model.Film;
import ru.yandex.practicum.filmorate.Model.Genre;
import ru.yandex.practicum.filmorate.Model.Mpa;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmDbStorageTest {

    private final FilmDbStorage filmDbStorage;
     Film film1;
     Film film2;
     Film film3;
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
    }

    @Test
    void getFilmById() {
        filmDbStorage.addNew(film1);

        Optional<Film> film = filmDbStorage.getFilmById(1);

        assertThat(film)
                .isPresent()
                .hasValueSatisfying(film4 ->
                        assertThat(film4).hasFieldOrPropertyWithValue("id", 1)
                );

        assertThat(film)
                .isPresent()
                .hasValueSatisfying(film4 ->
                        assertThat(film4).hasFieldOrPropertyWithValue("name", "name3")
                );
    }

    @Test
    void getAll() {
        filmDbStorage.addNew(film2);
        List<Film> allFilms = filmDbStorage.getAll();

        assertEquals(1, allFilms.size());
    }

    @Test
    void update() {
        filmDbStorage.update(film3);

        Optional<Film> film = filmDbStorage.getFilmById(1);

        assertThat(film)
                .isPresent()
                .hasValueSatisfying(film4 ->
                        assertThat(film4).hasFieldOrPropertyWithValue("id", 1)
                );

        assertThat(film)
                .isPresent()
                .hasValueSatisfying(film4 ->
                        assertThat(film4).hasFieldOrPropertyWithValue("name", "name3")
                );
    }
}