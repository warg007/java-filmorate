package ru.yandex.practicum.filmorate.Model;

import lombok.Data;
import ru.yandex.practicum.filmorate.Validation.MinimumDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class Film implements Comparable<Film> {
    private int id;
    @NotBlank
    @NotNull(message = "Название фильма не может быть пустым")
    private String name;
    @Size(max = 200, message = "Кол-во символов в описание должно быть не больше 200")
    private String description;
    @MinimumDate
    private LocalDate releaseDate;
    @Positive(message = "Продолжительность фильма должна быть положительной")
    private long duration;
    private Set<Integer> likesList = new HashSet<>();

    @Override
    public int compareTo(Film o) {
        return o.likesList.size() - likesList.size();
    }
}
