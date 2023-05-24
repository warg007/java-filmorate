package ru.yandex.practicum.filmorate.Controllers;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.Exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import javax.validation.Valid;
import java.util.HashMap;

@RestController
public class UserController {
    private HashMap<Integer, User> allUsers = new HashMap<>();
    private int idUsers = 1;

    @GetMapping("/users")
    public String getAllUsers() {
        return allUsers.values().toString();
    }

    @PutMapping("/users")
    public User update(@RequestBody User user) throws ValidationException {
        if (allUsers.containsKey(user.getId())) {
            allUsers.put(user.getId(), user);
            return user;
        } else {
            throw new ValidationException("Ошибка валидации пользователя");
            // я так предполагаю, что это сообщение надо отправлять клиенту или достаточно в консоль?
        }
    }

    @PostMapping("/users")
    public User addNewUser(@Valid @RequestBody User user) throws ValidationException {
        user.setId(idUsers++);
        allUsers.put(user.getId(), user);
        if (allUsers.containsKey(user.getId())) {
            return user;
        } else {
            throw new ValidationException("Ошибка валидации пользователя");
        }
    }
}
