package ru.yandex.practicum.filmorate.model;
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

    public User(int id, String email, String login, LocalDate birthday) {
        this.id = id;
        this.email = email;
        this.login = login;
        this.birthday = birthday;
        name = login;
    }

    public User(String email, String login, LocalDate birthday) {
        this.email = email;
        this.login = login;
        this.birthday = birthday;
        name = login;
    }

    public User(String email, String login, String name, LocalDate birthday) {
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
    }
}
