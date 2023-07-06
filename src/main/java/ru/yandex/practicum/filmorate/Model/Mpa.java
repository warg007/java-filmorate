package ru.yandex.practicum.filmorate.Model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Mpa {
    private Integer id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name;
}
