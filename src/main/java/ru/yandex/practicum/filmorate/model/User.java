package ru.yandex.practicum.filmorate.model;
import lombok.Builder;
import lombok.Data;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class User {
    private int id;
    @Email(message = "Не корректный формат электронной почты")
    private String email;
    @NotBlank(message = "Логин не может быть пустым")
    private String login;
    @NotNull
    private String name;
    @PastOrPresent(message = "Тебе нужно еще родиться")
    private LocalDate birthday;
}
