package ru.yandex.practicum.filmorate.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.Model.Genre;
import ru.yandex.practicum.filmorate.Model.Mpa;
import ru.yandex.practicum.filmorate.Storage.GenreDbStorage;
import ru.yandex.practicum.filmorate.Storage.MpaDbStorage;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MpaAndGenreService {

    private final MpaDbStorage mpaDbStorage;
    private final GenreDbStorage genreDbStorage;

    public List<Mpa> getAllMpa() {
        return mpaDbStorage.getAll();
    }

    public Mpa getMpaById(int id) {
        return mpaDbStorage.getById(id);
    }

    public List<Genre> getAllGenres() {
        return genreDbStorage.allGenres();
    }

    public Genre getGenreById(int id) {
        return genreDbStorage.oneGenre(id);
    }
}
