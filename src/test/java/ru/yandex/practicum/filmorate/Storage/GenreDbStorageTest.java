package ru.yandex.practicum.filmorate.Storage;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.Model.Genre;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class GenreDbStorageTest {
    private final GenreDbStorage genreDbStorage;
    @Test
    void allGenres() {
        List<Genre> allGenre = genreDbStorage.allGenres();

        assertEquals(6, allGenre.size());
    }

    @Test
    void oneGenre() {
        Genre genre = genreDbStorage.oneGenre(1);

        assertThat(genre)
                .hasFieldOrPropertyWithValue("id", 1);
        assertThat(genre)
                .hasFieldOrPropertyWithValue("name", "Комедия");
    }
}