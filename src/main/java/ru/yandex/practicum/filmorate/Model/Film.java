package ru.yandex.practicum.filmorate.Model;

import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class Film implements Comparable<Film> {
    private int id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private long duration;
    private Set<Integer> likesList = new HashSet<>();

    @Override
    public int compareTo(Film o) {
        return o.likesList.size() - likesList.size();
    }
}
