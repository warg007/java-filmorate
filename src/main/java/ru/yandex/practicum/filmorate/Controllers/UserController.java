package ru.yandex.practicum.filmorate.Controllers;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.Exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class UserController {
    private HashMap<Integer, User> allUsers = new HashMap<>();
    private int idUsers = 1;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return new ArrayList<>(allUsers.values());
    }

    @PutMapping("/users")
    public User update(@RequestBody User user) throws ValidationException {
        if (allUsers.containsKey(user.getId())) {
            allUsers.put(user.getId(), user);
            return user;
        } else {
            throw new ValidationException("Ошибка валидации пользователя");
        }
    }

    @PostMapping("/users")
    public User addNewUser(@Valid @RequestBody User user) throws ValidationException {
        user.setId(idUsers++);
        allUsers.put(user.getId(), user);
        if (user.getName() == null || user.getName().isEmpty() || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        if (allUsers.containsKey(user.getId())) {
            return user;
        } else {
            throw new ValidationException("Ошибка валидации пользователя");
        }
    }
}
