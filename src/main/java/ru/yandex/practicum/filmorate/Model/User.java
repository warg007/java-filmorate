package ru.yandex.practicum.filmorate.Model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
public class User {
    private int id;
    @Email(message = "Введена некорректная электронная почта")
    private String email;
    @NotNull(message = "Логин не может быть пустым и не должен содержать пробелы")
    @NotBlank(message = "Логин не может быть пустым и не должен содержать пробелы")
    private String login;
    private String name;
    @Past(message = "Нужно родиться")
    private LocalDate birthday;
    private final Set<Integer> friendsList = new HashSet<>();
}
