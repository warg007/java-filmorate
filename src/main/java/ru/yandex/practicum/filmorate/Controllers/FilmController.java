package ru.yandex.practicum.filmorate.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.Exceptions.ValidException;
import ru.yandex.practicum.filmorate.Model.Film;
import ru.yandex.practicum.filmorate.Service.FilmService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Valid
public class FilmController {
    private final FilmService filmService;

    @GetMapping("/films")
    public List<Film> getAll() {
        return filmService.getAll();
    }

    @GetMapping("/films/{id}")
    public Film getOne(@PathVariable int id) {
        return filmService.getFilmByIdService(id);
    }

    @PutMapping("/films")
    public Film update(@Valid @RequestBody Film film) throws ValidException {
        filmService.update(film);
        return film;
    }

    @PostMapping("/films")
    public Film addNew(@Valid @RequestBody Film film) throws ValidException {
        filmService.addNew(film);
        return film;
    }

    @PutMapping("/films/{id}/like/{userId}")
    public void likedFilm(@PathVariable int id, @PathVariable int userId) {
       filmService.likedFilm(userId, id);
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    public void deleteLike(@PathVariable int id, @PathVariable int userId) {
        filmService.deleteLike(userId, id);
    }

    @GetMapping("/films/popular")
    public ResponseEntity<List<Film>> topLiked(@RequestParam(required = false) Optional<Integer> count) {
        return ResponseEntity.status(HttpStatus.OK).body(filmService.topLiked(count));
    }
}
