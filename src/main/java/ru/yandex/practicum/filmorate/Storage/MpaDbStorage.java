package ru.yandex.practicum.filmorate.Storage;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.Exceptions.DataNotExistsException;
import ru.yandex.practicum.filmorate.Model.Film;
import ru.yandex.practicum.filmorate.Model.Mpa;

import java.util.List;

@Component
public class MpaDbStorage {
    private final JdbcTemplate jdbcTemplate;

    private MpaDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Mpa mpaOfOneFilm(Film film) {
        String link = "select * from mpa_and_film left join mpa on mpa_and_film.id_mpa = mpa.id where id_film = " + film.getId();
        return this.jdbcTemplate.queryForObject(link, (resultSet, rowNum) -> {
            Mpa mpa = new Mpa();
            mpa.setId(resultSet.getInt("id_mpa"));
            mpa.setName(resultSet.getString("name"));
            return mpa;
        });
    }

    public void addNew(Film film) {
        int mpaId = film.getMpa().getId();
        jdbcTemplate.update("insert into mpa_and_film (id_film, id_mpa) values (?, ?)", film.getId(), mpaId);
    }

    public void update(Film film) {
        jdbcTemplate.update("delete from mpa_and_film where id_film = ?", film.getId());
        int mpaId = film.getMpa().getId();
        jdbcTemplate.update("insert into mpa_and_film (id_film, id_mpa) values (?, ?)", film.getId(), mpaId);
    }

    public List<Mpa> getAll() {
        return this.jdbcTemplate.query("select * from mpa", (resultSet, rowNum) -> {
            Mpa mpa = new Mpa();
            mpa.setId(resultSet.getInt("id"));
            mpa.setName(resultSet.getString("name"));
            return mpa;
        });
    }

    public Mpa getById(int id) {
        try {
            return jdbcTemplate.queryForObject("select * from mpa where id = ?", new Object[]{id}, (resultSet, rowNum) -> {
                Mpa mpa = new Mpa();
                mpa.setId(resultSet.getInt("id"));
                mpa.setName(resultSet.getString("name"));
                return mpa;
            });
        } catch (EmptyResultDataAccessException e) {
            throw new DataNotExistsException("Не найден Мра с id: " + id);
        }
    }
}
