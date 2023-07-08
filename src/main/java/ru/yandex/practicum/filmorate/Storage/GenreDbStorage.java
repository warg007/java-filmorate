package ru.yandex.practicum.filmorate.Storage;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.Exceptions.DataNotExistsException;
import ru.yandex.practicum.filmorate.Model.Film;
import ru.yandex.practicum.filmorate.Model.Genre;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class GenreDbStorage {
    private final JdbcTemplate jdbcTemplate;

    public GenreDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Genre> allGenreOfOneFilm(Film film) {
        String SQLlink = "select * from genres left join film_genre on genres.id = film_genre.id_genre where id_film = " + film.getId();
        return this.jdbcTemplate.query(SQLlink, (resultSet, rowNum) -> {
            Genre genre = new Genre();
            genre.setId(resultSet.getInt("id"));
            genre.setName(resultSet.getString("name"));
            return genre;
        });
    }

    public void addNew(Film film) {
        Set<Genre> genreSet = new HashSet<>(film.getGenres());
        for (Genre i: genreSet) {
            int idGenre = i.getId();
            jdbcTemplate.update("insert into film_genre (id_film, id_genre) values (?, ?)", film.getId(), idGenre);
        }
    }

    public void update(Film film) {
        jdbcTemplate.update("delete from film_genre where id_film = ?", film.getId());
        addNew(film);
    }

    public List<Genre> allGenres() {
        return jdbcTemplate.query("select * from genres", (resultSet, rowNum) -> {
            Genre genre = new Genre();
            genre.setId(resultSet.getInt("id"));
            genre.setName(resultSet.getString("name"));
            return genre;
        });
    }

    public Genre oneGenre(int id) {
        try {
            return jdbcTemplate.queryForObject("select * from genres where id = ?", new Object[]{id}, (resultSet, rowNum) -> {
                Genre genre = new Genre();
                genre.setId(resultSet.getInt("id"));
                genre.setName(resultSet.getString("name"));
                return genre;
            });
        } catch (EmptyResultDataAccessException e) {
            throw new DataNotExistsException("Не найден Genre с id: " + id);
        }
    }
}
