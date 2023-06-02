package ru.yandex.practicum.filmorate.Storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.Model.Film;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

@Component
public class InMemoryFilmStorage implements FilmStorage{
    private final HashMap<Integer, Film> filmStorage = new HashMap<>();
    private final Set<Film> sortedByLikes = new TreeSet<>();
    private int filmId = 1;

    @Override
    public List<Film> getAll() {
        return new ArrayList<>(filmStorage.values());
    }

    @Override
    public Optional<Film> update(Film film) {
        if (filmStorage.containsKey(film.getId())) {
            filmStorage.put(film.getId(), film);
            return Optional.of(film);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Film addNew(Film film) {
        film.setId(filmId++);
        filmStorage.put(film.getId(), film);
        return film;
    }

    @Override
    public void delete(Integer id) {
        filmStorage.remove(id);
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
        int filmCount = 0;
        ArrayList<Film> answer = new ArrayList<>();
        List<Film> timeless = List.copyOf(sortedByLikes);
        if (count.isEmpty()) {
            if (timeless.size() > 10) {
                filmCount = 10;
            } else {
                filmCount = timeless.size();
            }
        } else {
            if (count.get() > timeless.size()) {
                filmCount = timeless.size();
            } else {
                filmCount = count.get();
            }
        }
        for (int i = 0; i < filmCount; i++) {
            answer.add(timeless.get(i));
        }
        return answer;
    }
}
