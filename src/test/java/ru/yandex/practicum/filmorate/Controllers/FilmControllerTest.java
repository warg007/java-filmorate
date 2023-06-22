package ru.yandex.practicum.filmorate.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.filmorate.Model.Film;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class FilmControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    Film film1 = Film.builder()
            .description("film1")
            .name("film1")
            .duration(10)
            .releaseDate(LocalDate.of(2000,12,12))
            .build();

    Film film2 = Film.builder()
            .description("film2")
            .name("film2")
            .duration(10)
            .releaseDate(LocalDate.of(2002,12,12))
            .build();

    @Test
    void shouldAddNewFilms() throws Exception {
        mockMvc.perform(
                        post("/films")
                                .content(objectMapper.writeValueAsString(film1))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("film1"))
                .andExpect(jsonPath("$.description").value("film1"))
                .andExpect(jsonPath("$.releaseDate").value("2000-12-12"));

        mockMvc.perform(
                post("/films")
                        .content(objectMapper.writeValueAsString(film2))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.id").value("2"))
                .andExpect(jsonPath("$.name").value("film2"))
                .andExpect(jsonPath("$.description").value("film2"))
                .andExpect(jsonPath("$.releaseDate").value("2002-12-12"));
    }

    @Test
    void shouldGetAllFilms() throws Exception {

    }
}