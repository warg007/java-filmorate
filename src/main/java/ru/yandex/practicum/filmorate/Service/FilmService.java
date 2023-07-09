package ru.yandex.practicum.filmorate.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.Exceptions.DataNotExistsException;
import ru.yandex.practicum.filmorate.Exceptions.HandlerNullPointException;
import ru.yandex.practicum.filmorate.Model.Film;
import ru.yandex.practicum.filmorate.Storage.FilmDbStorage;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class FilmService {
    private final FilmDbStorage filmStorage;
    private final UserService userService;

    public List<Film> getAll() {
        return filmStorage.getAll();
    }

    public Film update(Film film) {
        Optional<Film> timeless = filmStorage.update(film);
        if (timeless.isPresent()) {
            log.info("Обновлены данные фильма: " + timeless);
            return timeless.get();
        } else {
            throw new HandlerNullPointException("Фильм с id " + film.getId() + " для обновления не найден");
        }

    }

    public Film addNew(Film film) {
        Film timeless = filmStorage.addNew(film);
        log.info("Новый фильм сохранен: " + timeless);
        return timeless;
    }

    public Film getFilmByIdService(int id) {
        return filmStorage.getFilmById(id)
                .orElseThrow(() -> new DataNotExistsException("Не найден фильм с id: " + id));
    }

    public void likedFilm(int userId, int filmId) {
        String userName = userService.getUserByIdService(userId).getName();
        String filmName = getFilmByIdService(filmId).getName();
        filmStorage.likedFilm(userId, filmId);
        log.info(userName + " поставил(а) лайк фильму " + filmName);
    }

    public void deleteLike(int userId, int filmId) {
        String userName = userService.getUserByIdService(userId).getName();
        String filmName = getFilmByIdService(filmId).getName();
        filmStorage.deleteLike(userId, filmId);
        log.info(userName + " удалил(а) свой лайк у фильма " + filmName);
    }

    public List<Film> topLiked(Optional<Integer> count) {
        return filmStorage.getSortedByLikes(count);
    }
}
