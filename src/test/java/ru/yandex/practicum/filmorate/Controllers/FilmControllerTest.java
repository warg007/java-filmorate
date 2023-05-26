package ru.yandex.practicum.filmorate.Controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {

    FilmController filmController;

    Film filmOk1 = Film.builder().
            name("film1").
            id(1).
            description("film1").
            releaseDate(LocalDate.of(2000, 10, 10)).
            duration(10).
            build();

    Film filmOk2 = Film.builder().
            name("film2").
            id(2).
            description("film2").
            releaseDate(LocalDate.of(2020, 10, 10)).
            duration(10).
            build();

    Film filmWithoutName = Film.builder().
            name(" ").
            id(3).
            description("film3").
            releaseDate(LocalDate.of(2020, 10, 10)).
            duration(10).
            build();

    Film filmDescriptionLenghtMore200 = Film.builder().
            name("film4").
            id(4).
            description("film4film4film4film4film4film4film4film4film4film4film4film4film4film4film4film4film4film4film4" +
                    "film4film4film4film4film4film4film4film4film4film4film4film4film4film4film4film4film4film4film4" +
                    "film4film4film4film4film4film4film4film4film4film4film4film4film4film4film4film4film4film4film4").
            releaseDate(LocalDate.of(2020, 10, 10)).
            duration(10).
            build();

    Film filmWithWrongData1 = Film.builder().
            name("film5").
            id(5).
            description("film5").
            releaseDate(LocalDate.of(2222, 10, 10)).
            duration(10).
            build();

    Film filmWithWrongData2 = Film.builder().
            name("film6").
            id(6).
            description("film5").
            releaseDate(LocalDate.of(1, 10, 10)).
            duration(10).
            build();

    Film filmWithWrongDuration = Film.builder().
            name("film7").
            id(7).
            description("film7").
            releaseDate(LocalDate.of(2000, 10, 10)).
            duration(-10).
            build();

    @BeforeEach
    void clearController() {
        filmController = new FilmController();
    }

    @Test
    void shouldReturnListWithFilms() {
        filmController.addNewFilm(filmOk1);
        filmController.addNewFilm(filmOk2);

        List<Film> expected = new ArrayList<>();
        expected.add(filmOk1);
        expected.add(filmOk2);

        assertArrayEquals(expected.toArray(), filmController.getAllFilms().toArray(), "Кол-во фильмов не совпадает");
    }

    @Test
    void shouldAddOrNotFilms() {
        filmController.addNewFilm(filmOk1);
        filmController.addNewFilm(filmOk2);
        filmController.addNewFilm(filmDescriptionLenghtMore200);
        filmController.addNewFilm(filmWithoutName);
        filmController.addNewFilm(filmWithWrongDuration);
        filmController.addNewFilm(filmWithWrongData1);
        filmController.addNewFilm(filmWithWrongData2);

        List<Film> expected = new ArrayList<>();
        expected.add(filmOk1);
        expected.add(filmOk2);

        assertArrayEquals(expected.toArray(), filmController.getAllFilms().toArray(),"Кол-во фильмов не совпадает");
    }

    @Test
    void shouldUpdateOrNot() {
        filmController.addNewFilm(filmOk1);
        filmOk2.setId(1);
        filmController.updateFilm(filmOk2);
        filmDescriptionLenghtMore200.setId(1);
        filmController.updateFilm(filmDescriptionLenghtMore200);
        filmWithoutName.setId(1);
        filmController.updateFilm(filmWithoutName);
        filmWithWrongData1.setId(1);
        filmController.updateFilm(filmWithWrongData1);
        filmWithWrongData2.setId(1);
        filmController.updateFilm(filmWithWrongData2);
        filmWithWrongDuration.setId(1);
        filmController.updateFilm(filmWithWrongDuration);

        Film expected = filmOk2;
        List<Film> timeless = filmController.getAllFilms();
        Film actual = timeless.get(0);

        assertEquals(expected, actual, "Фильмы разные");
    }
}