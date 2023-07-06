package ru.yandex.practicum.filmorate.Storage;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.Model.Film;
import ru.yandex.practicum.filmorate.Model.Genre;
import ru.yandex.practicum.filmorate.Model.Mpa;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@Qualifier

public class FilmDbStorage implements  FilmStorage{

    private final  JdbcTemplate jdbcTemplate;
    private int id = 1;

    private FilmDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Film> getAll() {
        return this.jdbcTemplate.query(
                "select films.id as id_film, films.name as name_film, description, releasedate, duration, rate, id_genre," +
                        "genres.name as name_genre, id_mpa, mpa.name as name_mpa, id_user from films " +
                        "left join film_genre on films.id = film_genre.id_film " +
                        "left join likes on films.id = likes.id_film " +
                        "left join mpa_and_film on films.id = mpa_and_film.id_film " +
                        "left join mpa on mpa_and_film.id_mpa = mpa.id " +
                        "left join genres on film_genre.id_genre = genres.id " +
                        "group by films.id",
                (resultSet, rowNum) -> {
                    Film film = new Film();
                    film.setId(resultSet.getInt("ID_FILM"));
                    film.setName(resultSet.getString("NAME_FILM"));
                    film.setDescription(resultSet.getString("description"));
                    film.setReleaseDate(resultSet.getDate("releaseDate").toLocalDate());
                    film.setDuration(resultSet.getLong("duration"));
                    film.setRate(resultSet.getInt("rate"));
                    if (resultSet.getInt("id_mpa") != 0) {
                        film.setMpa(new Mpa(resultSet.getInt("id_mpa"), resultSet.getString("NAME_MPA")));
                    } else {
                        film.setMpa(new Mpa());
                    }
                    if (resultSet.getInt("id_genre") != 0) {
                        film.setGenres(List.of(new Genre(resultSet.getInt("id_genre"), resultSet.getString("NAME_GENRE"))));
                    } else {
                        film.setGenres(List.of());
                    }
                    return film;
                }
        );
    }

    @Override
    public Optional<Film> update(Film film) {
        if (getFilmById(film.getId()).isPresent()) {
            jdbcTemplate.update("update films set name = ?, description = ?, releaseDate = ?, duration = ?, rate = ? where id = ?",
                    film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(), film.getRate(), film.getId());
            if (film.getGenres() == null) {
                jdbcTemplate.update("delete from film_genre where id_film = ?", film.getId());
            } else {
                for (Genre i : film.getGenres()) {
                    int genreId = i.getId();
                    jdbcTemplate.update("update film_genre set id_genre = ? where id_film = ?",
                            genreId, film.getId());
                }
            }
            if (film.getMpa() == null) {
                jdbcTemplate.update("delete from mpa_and_film where id_film = ?", film.getId());
            } else {
                int mpaId = film.getMpa().getId();
                jdbcTemplate.update("update mpa_and_film set id_mpa = ? where id_film = ?",
                        mpaId, film.getId());
            }
            return Optional.of(film);

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
        if (film.getGenres() !=  null) {
            for (Genre i: film.getGenres()) {
                int idGenre = i.getId();
                jdbcTemplate.update("insert into film_genre (id_film, id_genre) values (?, ?)", film.getId(), idGenre);
            }
        }
        if (film.getMpa() != null) {
            int mpaId = film.getMpa().getId();
            jdbcTemplate.update("insert into mpa_and_film (id_film, id_mpa) values (?, ?)",
                    film.getId(), mpaId);
        }
        return film;
    }

    @Override
    public Optional<Film> getFilmById(int id) {
        SqlRowSet filmRows = jdbcTemplate.queryForRowSet(
                "select films.id as id_film, films.name as name_film, description, releasedate, duration, rate, id_genre," +
                        "genres.name as name_genre, id_mpa, mpa.name as name_mpa, id_user from films " +
                        "left join film_genre on films.id = film_genre.id_film " +
                        "left join likes on films.id = likes.id_film " +
                        "left join mpa_and_film on films.id = mpa_and_film.id_film " +
                        "left join mpa on mpa_and_film.id_mpa = mpa.id " +
                        "left join genres on film_genre.id_genre = genres.id " +
                        "where films.id = ? group by films.id", id);
        //SqlRowSet filmRows = jdbcTemplate.queryForRowSet("select * from films where id = ?", id);
        if (filmRows.next()) {
            Film film = new Film(
                    filmRows.getInt("ID_FILM"),
                    filmRows.getString("NAME_FILM"),
                    filmRows.getString("description"),
                    Objects.requireNonNull(filmRows.getDate("releasedate")).toLocalDate(),
                    filmRows.getLong("duration"),
                    filmRows.getInt("rate"),
                    new Mpa(filmRows.getInt("id_mpa"), filmRows.getString("NAME_MPA")),
                    List.of(new Genre(filmRows.getInt("id_genre"), filmRows.getString("NAME_GENRE")))
            );
            return Optional.of(film);
        } else {
            return Optional.empty();
        }
    }

    public void likedFilm() {

    }
}
