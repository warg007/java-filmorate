package ru.yandex.practicum.filmorate.Storage;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.Model.Mpa;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class MpaDbStorageTest {
    private final MpaDbStorage mpaDbStorage;

    @Test
    void getAll() {
        List<Mpa> allMpa = mpaDbStorage.getAll();

        assertEquals(5, allMpa.size());
    }

    @Test
    void getById() {
        Mpa mpa = mpaDbStorage.getById(1);

        assertThat(mpa)
                .hasFieldOrPropertyWithValue("id", 1);
        assertThat(mpa)
                .hasFieldOrPropertyWithValue("name", "G");
    }
}