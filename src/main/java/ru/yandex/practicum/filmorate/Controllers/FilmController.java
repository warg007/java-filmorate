package ru.yandex.practicum.filmorate.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.Exceptions.ValidException;
import ru.yandex.practicum.filmorate.Model.Film;
import ru.yandex.practicum.filmorate.Service.FilmService;

import javax.validation.Valid;
import java.util.List;

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

//    @PutMapping("/films/{id}/like/{userId}")
//    public void likedFilm(@PathVariable int id, @PathVariable int userId) {
//       filmService.likedFilm(userId, id);
//    }
//
//    @DeleteMapping("/films/{id}/like/{userId}")
//    public void deleteLike(@PathVariable int id, @PathVariable int userId) {
//        filmService.deleteLike(userId, id);
//    }
//
//    @GetMapping("/films/popular")
//    public ResponseEntity<List<Film>> topLiked(@RequestParam(required = false) Optional<Integer> count) {
//        return ResponseEntity.status(HttpStatus.OK).body(filmService.topLiked(count));
//    }
}
