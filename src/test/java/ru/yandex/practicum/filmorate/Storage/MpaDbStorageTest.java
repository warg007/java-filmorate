package ru.yandex.practicum.filmorate.Storage;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.*;
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
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MpaDbStorageTest {
    private final MpaDbStorage mpaDbStorage;
    private final FilmDbStorage filmDbStorage;

    Film film;

    @BeforeEach
    private void init() {
        film = new Film(1,
                "name",
                "des",
                LocalDate.of(2000,12,12),
                10,
                1,
                new Mpa(1, "G"),
                List.of(new Genre(1, "Комедия")));
    }

    @Test
    @Order(1)
    void testGetAll() {
        List<Mpa> allMpa = mpaDbStorage.getAll();

        assertEquals(5, allMpa.size());
    }

    @Test
    @Order(2)
    void testGetById() {
        Mpa mpa = mpaDbStorage.getById(1);

        assertThat(mpa)
                .hasFieldOrPropertyWithValue("id", 1);
        assertThat(mpa)
                .hasFieldOrPropertyWithValue("name", "G");
    }

    @Test
    @Order(3)
    void testMpaOfOneFilm() {
        filmDbStorage.addNew(film);
        Mpa expected = mpaDbStorage.mpaOfOneFilm(film);

        assertThat(expected)
                .hasFieldOrPropertyWithValue("id", 1)
                .hasFieldOrPropertyWithValue("name", "G");
    }
}