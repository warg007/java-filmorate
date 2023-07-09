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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class GenreDbStorageTest {
    private final GenreDbStorage genreDbStorage;
    private final FilmDbStorage filmDbStorage;

    Film film;

    @BeforeEach
    private void init() {
        film = new Film(1,
                "name",
                "des",
                LocalDate.of(1900,12,12),
                10,
                1,
                new Mpa(1, "G"),
                List.of(new Genre(1, "Комедия")));
    }

    @Test
    void testAllGenres() {
        List<Genre> allGenre = genreDbStorage.allGenres();

        assertEquals(6, allGenre.size());
    }

    @Test
    void testOneGenre() {
        Genre genre = genreDbStorage.oneGenre(1);

        assertThat(genre)
                .hasFieldOrPropertyWithValue("id", 1)
                .hasFieldOrPropertyWithValue("name", "Комедия");
    }

    @Test
    void testAllGenreOfOneFilm() {
        filmDbStorage.addNew(film);
        List<Genre> allGenres = genreDbStorage.allGenreOfOneFilm(film);
        Genre genre = allGenres.get(0);

        assertThat(genre)
                .hasFieldOrPropertyWithValue("id", 1)
                .hasFieldOrPropertyWithValue("name", "Комедия");
    }

}