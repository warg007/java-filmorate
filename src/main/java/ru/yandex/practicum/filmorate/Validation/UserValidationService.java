package ru.yandex.practicum.filmorate.Validation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.Exceptions.ValidException;
import ru.yandex.practicum.filmorate.Model.User;

@Component
@Slf4j
public class UserValidationService {

    private void validLogin(User user) throws ValidException {
        if (user.getLogin().contains(" ")) {
            throw new ValidException("Логин не может быть пустым и не должен содержать пробелы");
        }
    }

    private void validName(User user) {
        if (user.getName() == null || user.getName().isEmpty() || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }

    public void validUser(User user) throws ValidException {
        validLogin(user);
        validName(user);
    }
}
