package ru.yandex.practicum.filmorate.Validation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.Exceptions.ValidException;
import ru.yandex.practicum.filmorate.Model.User;
import java.time.LocalDate;

@Component
@Slf4j
public class UserValidationService {

    private void validEmail(User user) throws ValidException {
        if (!user.getEmail().contains("@") &&
                !user.getEmail().contains(".") &&
                !user.getEmail().contains("ru")) {
            throw new ValidException("Некорректный формат электронной почты");
        }
        if (user.getEmail().contains(" ")) {
            throw new ValidException("Некорректный формат электронной почты");
        }
    }

    private void validLogin(User user) throws ValidException {
        if (user.getLogin().isBlank() || user.getLogin().isEmpty() ||
                user.getLogin().contains(" ")) {
            throw new ValidException("Логин не может быть пустым и не должен содержать пробелы");
        }
    }

    private void validName(User user) {
        if (user.getName() == null || user.getName().isEmpty() || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }

    private void validBirthday(User user) throws ValidException {
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidException("Нужно родиться");
        }
    }

    public void validUser(User user) throws ValidException {
        validEmail(user);
        validLogin(user);
        validName(user);
        validBirthday(user);
    }
}
