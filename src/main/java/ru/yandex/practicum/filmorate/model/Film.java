package ru.yandex.practicum.filmorate.model;
import lombok.Data;
import ru.yandex.practicum.filmorate.Controllers.validation.MinimumDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;


@Data
public class Film {
    private int id;
    @NotBlank(message = "Поле имя не может быть пустым")
    private String name;
    @Size(min = 0, max = 200, message = "Максимальное кол-во символов 200")
    private String description;
    @MinimumDate(message = "Введите корректную дату")
    private LocalDate releaseDate;
    @Positive(message = "Продолжительность должна быть положительной")
    private long duration;
}
