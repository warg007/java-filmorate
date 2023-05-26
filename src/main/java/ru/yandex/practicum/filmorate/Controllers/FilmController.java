package ru.yandex.practicum.filmorate.Controllers;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.yandex.practicum.filmorate.Controllers.validation.FilmValidationService;
import ru.yandex.practicum.filmorate.Exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class FilmController {
    private Map<Integer, Film> allFilms = new HashMap<>();
    private int idFilms = 1;
    FilmValidationService filmValid = new FilmValidationService();
    private static final Logger log = LoggerFactory.getLogger(Film.class);
    Gson gson = new Gson();

    @GetMapping("/films")
    public List<Film> getAllFilms() {
        return new ArrayList<>(allFilms.values());
    }

    @PutMapping("/films")
    public ResponseEntity updateFilm(@RequestBody Film film) {
        if (allFilms.containsKey(film.getId())) {
            try {
                filmValid.validFilm(film);
                allFilms.put(film.getId(), film);
                log.info("Фильм обновлен: " + film);
            } catch (ValidationException e) {
                log.info("Ошибка обновления фильма: " + e.getMessage());
                return ResponseEntity.badRequest().body(gson.toJson("Ошибка обновления фильма: " + e.getMessage()));
            }
        } else {
            log.info("Не найден фильм, который нужно обновить");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(gson.toJson("Не найден фильм, который нужно обновить"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(film);
    }

    @PostMapping("/films")
    public ResponseEntity addNewFilm(@RequestBody Film film) {
        try {
            filmValid.validFilm(film);
            film.setId(idFilms++);
            allFilms.put(film.getId(), film);
            log.info("Сохранен фильм: " + film);
        } catch (ValidationException e) {
            log.info("Ошибка сохранения фильма: " + e.getMessage());
            return ResponseEntity.badRequest().body(gson.toJson("Ошибка сохранения фильма: " + e.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(film);
    }
}
