package ru.yandex.practicum.filmorate.Storage.OldStorage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.Model.Film;
import ru.yandex.practicum.filmorate.Storage.FilmStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> filmStorage = new HashMap<>();
    private final Set<Film> sortedByLikes = new TreeSet<>();
    private int filmId = 1;

    @Override
    public List<Film> getAll() {
        return new ArrayList<>(filmStorage.values());
    }

    @Override
    public Optional<Film> update(Film film) {
        if (filmStorage.containsKey(film.getId())) {
            deleteFromTreeSet(filmStorage.get(film.getId()));
            filmStorage.put(film.getId(), film);
            addToTreeSet(film);
            return Optional.of(film);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Film addNew(Film film) {
        film.setId(filmId++);
        filmStorage.put(film.getId(), film);
        addToTreeSet(film);
        return film;
    }


    @Override
    public Optional<Film> getFilmById(int id) {
        if (filmStorage.containsKey(id)) {
            return Optional.of(filmStorage.get(id));
        } else {
            return Optional.empty();
        }
    }

    public void addToTreeSet(Film film) {
        sortedByLikes.add(film);
    }

    public void deleteFromTreeSet(Film film) {
        sortedByLikes.remove(film);
    }

    public List<Film> getSortedByLikes(Optional<Integer> count) {
        return count.map(s -> sortedByLikes.stream()
                .limit(s)
                .collect(Collectors.toList()))
                .orElseGet(() -> sortedByLikes.stream()
                .limit(10)
                .collect(Collectors.toList()));
    }
}
