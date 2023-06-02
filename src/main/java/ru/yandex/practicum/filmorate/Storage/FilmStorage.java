package ru.yandex.practicum.filmorate.Storage;

import ru.yandex.practicum.filmorate.Model.Film;
import java.util.List;
import java.util.Optional;

public interface FilmStorage {
    List<Film> getAll();

    Optional<Film> update(Film film);

    Film addNew(Film film);

    void delete(Integer id);

    Optional<Film> getFilmById(int id);
}
