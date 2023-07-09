package ru.yandex.practicum.filmorate.Storage;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.Model.Film;
import ru.yandex.practicum.filmorate.RowMapper.FilmMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Qualifier

public class FilmDbStorage implements FilmStorage {

    private final  JdbcTemplate jdbcTemplate;
    private final GenreDbStorage genreDbStorage;
    private final MpaDbStorage mpaDbStorage;
    private int id = 1;

    private FilmDbStorage(JdbcTemplate jdbcTemplate, GenreDbStorage genreDbStorage, MpaDbStorage mpaDbStorage) {
        this.jdbcTemplate = jdbcTemplate;
        this.genreDbStorage = genreDbStorage;
        this.mpaDbStorage = mpaDbStorage;
    }

    @Override
    public List<Film> getAll() {
        String link = "select * from films";
        return this.jdbcTemplate.query(link, new FilmMapper(mpaDbStorage, genreDbStorage));
    }

    @Override
    public Optional<Film> update(Film film) {
        if (getFilmById(film.getId()).isPresent()) {
            jdbcTemplate.update("update films set name = ?, description = ?, releaseDate = ?, duration = ?, rate = ? where id = ?",
                    film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(), film.getRate(), film.getId());
            genreDbStorage.update(film);
            mpaDbStorage.update(film);
            Film trueFilm = getFilmById(film.getId()).get();
            return Optional.of(trueFilm);
        } else {
            return Optional.empty();
        }
    }


    @Override
    public Film addNew(Film film) {
        film.setId(id++);
        jdbcTemplate.update("insert into films (id, name, description, releaseDate, duration, rate) values (?, ?, ?, ?, ?, ?)",
                film.getId(), film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(), film.getRate()
        );
        genreDbStorage.addNew(film);
        mpaDbStorage.addNew(film);
        return getFilmById(film.getId()).get();
    }

    @Override
    public Optional<Film> getFilmById(int id) {
        Film film;
        try {
            String link = "select * from films where id = ?";
            film = jdbcTemplate.queryForObject(link, new Object[]{id}, new FilmMapper(mpaDbStorage, genreDbStorage));
        } catch (Exception e) {
            return Optional.empty();
        }
        return Optional.of(film);
    }

    private boolean checkLikeByUser(int userId, int filmId) {
        String link = "select id_film from likes where id_user = " + userId;
        List<Integer> idFilm = jdbcTemplate.query(link, (resultSet, rowNum) -> resultSet.getInt("id_film"));
        return !idFilm.contains(filmId);
    }

    public void likedFilm(int userId, int filmId) {
        String link = "insert into likes (id_film, id_user) values (?, ?)";
        if (checkLikeByUser(userId, filmId) && getFilmById(filmId).isPresent()) {
            jdbcTemplate.update(link, filmId, userId);
            int updatedRate = getFilmById(filmId).get().getRate() + 1;
            Film updatedFilm = getFilmById(filmId).get();
            updatedFilm.setRate(updatedRate);
            update(updatedFilm);
        }
    }

    public void deleteLike(int userId, int filmId) {
        String link = "delete from likes where id_film = ? and id_user = ?";
        if (!(checkLikeByUser(userId, filmId)) && getFilmById(filmId).isPresent()) {
            jdbcTemplate.update(link, filmId, userId);
            int updatedRate = getFilmById(filmId).get().getRate() - 1;
            Film updatedFilm = getFilmById(filmId).get();
            updatedFilm.setRate(updatedRate);
            update(updatedFilm);
        }
    }

    public List<Film> getSortedByLikes(Optional<Integer> count) {
        String link = "select * from films order by rate desc";
        List<Film> sortedByLikes = jdbcTemplate.query(link, new FilmMapper(mpaDbStorage, genreDbStorage));
        return count.map(s -> sortedByLikes.stream()
                        .limit(s)
                        .collect(Collectors.toList()))
                .orElseGet(() -> sortedByLikes.stream()
                        .limit(10)
                        .collect(Collectors.toList()));
    }
}
