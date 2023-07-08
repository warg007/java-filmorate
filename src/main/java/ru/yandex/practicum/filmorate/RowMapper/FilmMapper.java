package ru.yandex.practicum.filmorate.RowMapper;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.Model.Film;
import ru.yandex.practicum.filmorate.Model.Mpa;
import ru.yandex.practicum.filmorate.Storage.GenreDbStorage;
import ru.yandex.practicum.filmorate.Storage.MpaDbStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class FilmMapper implements RowMapper<Film> {
    private final MpaDbStorage mpaStorage;
    private final GenreDbStorage genreStorage;

    public FilmMapper(MpaDbStorage mpaStorage, GenreDbStorage genreStorage) {
        this.mpaStorage = mpaStorage;
        this.genreStorage = genreStorage;
    }

    @Override
    public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
        Film film = new Film();
        film.setId(rs.getInt("ID"));
        film.setName(rs.getString("NAME"));
        film.setDescription(rs.getString("DESCRIPTION"));
        film.setReleaseDate(rs.getDate("RELEASEDATE").toLocalDate());
        film.setDuration(rs.getLong("DURATION"));
        film.setRate(rs.getInt("RATE"));
        try {
            film.setMpa(mpaStorage.mpaOfOneFilm(film));
        } catch (EmptyResultDataAccessException e) {
            film.setMpa(new Mpa());
        }
        try {
            film.setGenres(genreStorage.allGenreOfOneFilm(film));
        } catch (EmptyResultDataAccessException e) {
            film.setGenres(List.of());
        }
        return film;
    }
}
