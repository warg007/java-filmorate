package ru.yandex.practicum.filmorate.Validation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.Exceptions.ValidException;
import ru.yandex.practicum.filmorate.Model.Film;
import java.time.LocalDate;

@Slf4j
@Component
public class FilmValidationService {

    private void validName(Film film) throws ValidException {
        if (film.getName().isEmpty() || film.getName().isBlank()) {
            throw new ValidException("Название фильма не может быть пустым");
        }
    }

    private void validDescription(Film film) throws ValidException {
        if (film.getDescription().length() > 200) {
            throw new ValidException("Кол-во символов в описание должно быть не больше 200");
        }
    }

    private void validDuration(Film film) throws ValidException {
        if (film.getDuration() <= 0) {
            throw new ValidException("Продолжительность фильма должна быть положительной");
        }
    }

    private void validDate(Film film) throws ValidException {
        LocalDate dataOfFirstFilm = LocalDate.of(1895, 12, 28);
        if (film.getReleaseDate().isBefore(dataOfFirstFilm) || film.getReleaseDate().isAfter(LocalDate.now())) {
            throw new ValidException("Дата фильма не может быть раньше 1895-12-28 или в будущем");
        }
    }

    public void validFilm(Film film) throws ValidException {
        validDate(film);
        validDescription(film);
        validDuration(film);
        validName(film);
    }
}
