package ru.yandex.practicum.filmorate.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
}
