package ru.yandex.practicum.filmorate.Controllers;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.Controllers.validation.UserValidationService;
import ru.yandex.practicum.filmorate.Exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class UserController {
    private HashMap<Integer, User> allUsers = new HashMap<>();
    private int idUsers = 1;
    UserValidationService validUser = new UserValidationService();
    private static final Logger log = LoggerFactory.getLogger(User.class);
    Gson gson = new Gson();

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return new ArrayList<>(allUsers.values());
    }

    @PutMapping("/users")
    public ResponseEntity updateUser(@RequestBody User user) {
        if (allUsers.containsKey(user.getId())) {
            try {
                validUser.validUser(user);
                allUsers.put(user.getId(), user);
                log.info("Данные пользователя успешно обновлены: " + user);
            } catch (ValidationException e) {
                log.info("Ошибка обновления данных: " + e.getMessage());
                return ResponseEntity.badRequest().body(gson.toJson("Ошибка обновления данных: " + e.getMessage()));
            }
        } else {
            log.info("Не найден пользователь");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(gson.toJson("Не найден пользователь"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PostMapping("/users")
    public ResponseEntity addNewUser(@RequestBody User user) {
        try {
            validUser.validUser(user);
            user.setId(idUsers++);
            allUsers.put(user.getId(), user);
            log.info("Зарегистрирован новый пользователь: " + user);
        } catch (ValidationException e) {
            log.info("Ошибка регистрации нового пользователя: " + e.getMessage());
            return ResponseEntity.badRequest().body(gson.toJson("Ошибка регистрации нового пользователя: " + e.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
}
