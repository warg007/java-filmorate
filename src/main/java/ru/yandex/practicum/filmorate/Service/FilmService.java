package ru.yandex.practicum.filmorate.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.Exceptions.HandlerNullPointException;
import ru.yandex.practicum.filmorate.Model.Film;
import ru.yandex.practicum.filmorate.Storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.Validation.FilmValidationService;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class FilmService {
    private final InMemoryFilmStorage filmStorage;
    private final FilmValidationService filmValidationService;
    private final UserService userService;

    public List<Film> getAll() {
        return filmStorage.getAll();
    }

    public Film update(Film film) {
        filmValidationService.validFilm(film);
        if (filmStorage.getFilmById(film.getId()).isPresent()) {
            filmStorage.deleteFromTreeSet(getFilmByIdService(film.getId()));
            Film timeless = filmStorage.update(film).get();
            log.info("Обновлены данные фильма: " + film);
            filmStorage.addToTreeSet(timeless);
            return timeless;
        } else {
            throw new HandlerNullPointException("Фильм с id " + film.getId() + " для обновления не найден");
        }
    }

    public Film addNew(Film film) {
        filmValidationService.validFilm(film);
        Film timeless = filmStorage.addNew(film);
        filmStorage.addToTreeSet(timeless);
        log.info("Новый фильм сохранен: " + timeless);
        return timeless;
    }

    public Film getFilmByIdService(int id) {
        if (filmStorage.getFilmById(id).isPresent()) {
            return filmStorage.getFilmById(id).get();
        } else {
            throw new HandlerNullPointException("Не найден фильм с id: " + id);
        }
    }

    public void delete(int id) {
        filmStorage.delete(id);
        log.info("Запрос на удаление фильма с id: " + id);
    }

    public String likedFilm(int userId, int filmId) {
        String userName = userService.getUserByIdService(userId).getName();
        String filmName = getFilmByIdService(filmId).getName();
        getFilmByIdService(filmId).getLikesList().add(userId);
        filmStorage.addToTreeSet(getFilmByIdService(filmId));
        log.info(userName + " поставил(а) лайк фильму " + filmName);
        return userName + " поставил(а) лайк фильму " + filmName;
    }

    public String deleteLike(int userId, int filmId) {
        String userName = userService.getUserByIdService(userId).getName();
        String filmName = getFilmByIdService(filmId).getName();
        getFilmByIdService(filmId).getLikesList().remove(userId);
        filmStorage.addToTreeSet(getFilmByIdService(filmId));
        log.info(userName + " удалил(а) свой лайк у фильма " + filmName);
        return userName + " удалил(а) свой лайк у фильма " + filmName;
    }

    public List<Film> topLiked(Optional<Integer> count) {
        return filmStorage.getSortedByLikes(count);
    }
}
