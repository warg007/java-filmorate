package ru.yandex.practicum.filmorate.Model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.Validation.MinimumDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Film {
    private int id;
    @NotBlank(message = "Название фильма не может быть пустым")
    @NotNull(message = "Название фильма не может быть пустым")
    private String name;
    @Size(max = 200, message = "Кол-во символов в описание должно быть не больше 200")
    private String description;
    @MinimumDate
    private LocalDate releaseDate;
    @Positive(message = "Продолжительность фильма должна быть положительной")
    private long duration;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer rate;
    private Mpa mpa;
    private List<Genre> genres = new ArrayList<>();


}
