package ru.otus.library.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.otus.library.data.model.Genre;
import ru.otus.library.service.GenreService;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(GenreController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GenreControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private GenreService genreService;

    @DisplayName("Список жанров")
    @Order(0)
    @Test
    void getAllGenresTest() throws Exception {
        List<Genre> genreList = new ArrayList<>();

        Genre genre = new Genre();
        genre.setId("234");
        genre.setName("имя 21");
        genreList.add(genre);

        genre = new Genre();
        genre.setId("234");
        genre.setName("имя 21");
        genreList.add(genre);


        when(genreService.findAllGenre()).thenReturn(genreList);
        MvcResult result = mvc.perform(get("/api/genre/list"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andReturn();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        assertThat(content)
                .startsWith("[{").endsWith("}]")
                .contains("\"id\":", "\"name\":")
                .contains(genreList.get(0).getId(),
                        genreList.get(0).getName(),
                        genreList.get(1).getName());
    }

    @DisplayName("Получение жанра по ID")
    @Order(1)
    @Test
    void getGenreByIdTest() throws Exception {
        final String id = "43657fdg";
        Genre genre = new Genre();
        genre.setId(id);
        genre.setName("имя 21");

        when(genreService.getGenreById(id)).thenReturn(Optional.of(genre));
        MvcResult result = mvc.perform(get("/api/genre/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andReturn();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        assertThat(content)
                .startsWith("{").endsWith("}")
                .contains("\"id\":", "\"name\":")
                .contains(genre.getId(),
                        genre.getName());
    }

    @DisplayName("Добавление жанра")
    @Order(2)
    @Test
    void addGenreTest() throws Exception {
        String genreDto = "{\"name\": \"жанр\"}";
        mvc.perform(post("/api/genre").content(genreDto)
                .contentType("application/json")
                .accept("application/json"))
                .andExpect(status().isOk());
        verify(genreService).saveGenre(any());
    }


    @DisplayName("Удаление жанра")
    @Order(4)
    @Test
    void deleteGernreTest() throws Exception {
        final String id = "43657fdg";
        mvc.perform(delete("/api/genre/" + id))
                .andExpect(status().isOk());
        verify(genreService).deleteGenre(id);
    }
}