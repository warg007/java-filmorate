package ru.yandex.practicum.filmorate.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.Model.Genre;
import ru.yandex.practicum.filmorate.Model.Mpa;
import ru.yandex.practicum.filmorate.Service.MpaAndGenreService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MpaAndGenreController {
    private final MpaAndGenreService mpaAndGenreService;

    @GetMapping("/mpa")
    public List<Mpa> getAllMpa() {
        return mpaAndGenreService.getAllMpa();
    }

    @GetMapping("/mpa/{id}")
    public Mpa getOneMpa(@PathVariable int id) {
        return mpaAndGenreService.getMpaById(id);
    }

    @GetMapping("/genres")
    public List<Genre> getAllGenres() {
        return mpaAndGenreService.getAllGenres();
    }

    @GetMapping("/genres/{id}")
    public Genre getOneGenre(@PathVariable int id) {
        return mpaAndGenreService.getGenreById(id);
    }
}
