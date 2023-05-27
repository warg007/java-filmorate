package ru.yandex.practicum.filmorate.Controllers.validation;

import ru.yandex.practicum.filmorate.Exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import java.time.LocalDate;

public class FilmValidationService {

    private void validName(Film film) throws ValidationException {
        if (film.getName().isEmpty() || film.getName().isBlank()) {
            throw new ValidationException("Название фильма не может быть пустым");
        }
    }

    private void validDescription(Film film) throws ValidationException {
        if (film.getDescription().length() > 200) {
            throw new ValidationException("Кол-во символов в описание должно быть не более 200");
        }
    }

    private void validDuration(Film film) throws ValidationException {
        if (film.getDuration() <= 0) {
            throw new ValidationException("Продолжительность фильма должна быть положительной");
        }
    }

    private void validDate(Film film) throws ValidationException {
        LocalDate dataOfFirstFilm = LocalDate.of(1895, 12, 28);
        if (film.getReleaseDate().isBefore(dataOfFirstFilm) || film.getReleaseDate().isAfter(LocalDate.now())) {
            throw new ValidationException("Дата фильма не может быть раньше 1895-12-28");
        }
    }

    public void validFilm(Film film) throws ValidationException {
        validDate(film);
        validDescription(film);
        validDuration(film);
        validName(film);
    }
}
