package ru.otus.library.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.otus.library.data.library.model.Genre;
import ru.otus.library.service.library.GenreService;
import ru.otus.library.service.seurity.UserDetailsServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(GenreController.class)
class GenreControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private GenreService genreService;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @DisplayName("Список жанров")
    @Order(0)
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void listGenreTest() throws Exception {
        List<Genre> genres = new ArrayList<>();
        Genre genre = new Genre();
        genre.setName("жанр");
        genres.add(genre);

        when(genreService.findAllGenre()).thenReturn(genres);
        MvcResult result = mvc.perform(get("/genre/list"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andReturn();
        String content = result.getResponse().getContentAsString();
        assertThat(content)
                .contains(genres.get(0).getName());
    }


    @DisplayName("Добавление жанра /genre/add")
    @Test
    @Order(1)
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void addGenreTest() throws Exception {
        mvc.perform(get("/genre/add"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"));
    }


    @DisplayName("Добавлене автора /genre/edit")
    @Order(3)
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void insertGenreTest() throws Exception {
        mvc.perform(
                post("/genre/edit")
                        .param("name", "жанр1") // genreId = null
                       )
                .andExpect(status().is3xxRedirection());
        ArgumentCaptor<Genre> captor = ArgumentCaptor.forClass(Genre.class);
        verify(genreService).saveGenre(captor.capture());
    }

    @DisplayName("Редактирование жанра")
    @Order(3)
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void updateGenreTest() throws Exception {
        mvc.perform(
                post("/genre/edit")
                        .param("genreId", "4")
                        .param("name", "жанр1")
                       )
                .andExpect(status().is3xxRedirection());
        ArgumentCaptor<Genre> captor = ArgumentCaptor.forClass(Genre.class);
        verify(genreService).updateGenre(captor.capture());
    }


    @DisplayName("Удаление жанра")
    @Order(5)
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void deleteGenre() throws Exception {
        mvc.perform(
                get("/genre/delete")
                        .param("id", "456"))
                .andExpect(status().is3xxRedirection());
        verify(genreService).deleteGenre(anyLong());
    }
}