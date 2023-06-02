package ru.yandex.practicum.filmorate.Model;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class User {
    private int id;
    private String email;
    private String login;
    private String name;
    private LocalDate birthday;
    @Autowired
    private Set<Integer> friendsList = new HashSet<>();
}
