package ru.yandex.practicum.filmorate.Controllers;
import org.springframework.validation.annotation.Validated;
import ru.yandex.practicum.filmorate.Exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
@Validated
@RestController
public class FilmController {
    private Map<Integer, Film> allFilms = new HashMap<>();
    private int idFilms = 1;

    @GetMapping("/films")
    public String getAllFilms() {
        return allFilms.values().toString();
    }
    @PutMapping("/films")
    public Film updateFilm(@Valid @RequestBody Film film) throws ValidationException {
        if (allFilms.containsKey(film.getId())){
            allFilms.put(film.getId(), film);
            return film;
        } else {
            throw new ValidationException("Ошибка валидации фильма");
        }
    }
    @PostMapping("/films")
    public Film addNewFilm (@Valid @RequestBody Film film) throws ValidationException {
        film.setId(idFilms++);
        allFilms.put(film.getId(), film);
        if (allFilms.containsKey(film.getId())){
            return film;
        } else {
            throw new ValidationException("Ошибка валидации фильма");
        }
        /*
        Я видел как сделать более красиво без этой проверки if (если валидация не прошла и выкинуть ошибку)
        Но там большой кусок кода, который не понятный. А просто копировать без разбора нет смысла.
         */
    }
}
