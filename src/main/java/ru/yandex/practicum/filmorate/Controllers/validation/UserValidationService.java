package ru.yandex.practicum.filmorate.Controllers.validation;

import ru.yandex.practicum.filmorate.Exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

public class UserValidationService {

    private void validEmail(User user) throws ValidationException {
        if (!user.getEmail().contains("@") &&
        !user.getEmail().contains(".") &&
        !user.getEmail().contains("ru")) {
            throw new ValidationException("Некорректный формат электронной почты");
        }
        if (user.getEmail().contains(" ")) {
            throw new ValidationException("Некорректный формат электронной почты");
        }
    }

    private void validLogin(User user) throws ValidationException {
        if (user.getLogin().isBlank() || user.getLogin().isEmpty() ||
        user.getLogin().contains(" ")) {
            throw new ValidationException("Логин не может быть пусты и не должен содержать пробелы");
        }
    }

    private void validName(User user) {
        if (user.getName() == null || user.getName().isEmpty() || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }

    private void validBirthday(User user) throws ValidationException {
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Нужно родиться");
        }
    }

    public void validUser(User user) throws ValidationException {
        validEmail(user);
        validLogin(user);
        validName(user);
        validBirthday(user);
    }
}
