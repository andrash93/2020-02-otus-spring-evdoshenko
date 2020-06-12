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
import ru.otus.library.data.model.Author;
import ru.otus.library.service.AuthorService;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AuthorController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthorControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AuthorService authorService;


    @DisplayName("Список авторов")
    @Order(0)
    @Test
    void getAllAuthorsTest() throws Exception {
        List<Author> authorList = new ArrayList<>();

        Author author = new Author();
        author.setId("234");
        author.setName("имя 21");
        author.setCountry("Страна1");
        authorList.add(author);

        author = new Author();
        author.setId("234");
        author.setName("имя 21");
        author.setCountry("Страна1");
        authorList.add(author);


        when(authorService.findAllAuthors()).thenReturn(authorList);
        MvcResult result = mvc.perform(get("/api/author/list"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andReturn();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        assertThat(content)
                .startsWith("[{").endsWith("}]")
                .contains("\"id\":", "\"name\":", "\"country\":")
                .contains(authorList.get(0).getId(),
                        authorList.get(0).getName(), authorList.get(0).getCountry(),
                        authorList.get(1).getName(), authorList.get(1).getCountry());
    }

    @DisplayName("Поиск авторов по имени")
    @Order(0)
    @Test
    void findAuthorsByNameTest() throws Exception {
        String name = "имя 21";
        List<Author> authorList = new ArrayList<>();

        Author author = new Author();
        author.setId("234");
        author.setName("имя 21");
        author.setCountry("Страна1");
        authorList.add(author);

        author = new Author();
        author.setId("234");
        author.setName("имя 21");
        author.setCountry("Страна1");
        authorList.add(author);


        when(authorService.findAuthorsByName(name)).thenReturn(authorList);
        MvcResult result = mvc.perform(get("/api/author/find?name=" + name))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andReturn();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        assertThat(content)
                .startsWith("[{").endsWith("}]")
                .contains("\"id\":", "\"name\":", "\"country\":")
                .contains(authorList.get(0).getId(),
                        authorList.get(0).getName(), authorList.get(0).getCountry(),
                        authorList.get(1).getName(), authorList.get(1).getCountry());
    }

    @DisplayName("Получение автора по ID")
    @Order(1)
    @Test
    void getAuthorByIdTest() throws Exception {
        final String id = "43657fdg";
        Author author = new Author();
        author.setId(id);
        author.setName("имя 21");
        author.setCountry("Страна1");


        when(authorService.getAuthorById(id)).thenReturn(Optional.of(author));
        MvcResult result = mvc.perform(get("/api/author/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andReturn();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        assertThat(content)
                .startsWith("{").endsWith("}")
                .contains("\"id\":", "\"name\":", "\"country\":")
                .contains(author.getId(),
                        author.getName(), author.getCountry());
    }

    @DisplayName("Добавление автора")
    @Order(2)
    @Test
    void addAuthorTest() throws Exception {
        String authorDto = "{\"name\": \"автор1\"," +
                "\"country\": \"Страна\"}";
        mvc.perform(post("/api/author").content(authorDto)
                .contentType("application/json")
                .accept("application/json"))
                .andExpect(status().isOk());
        verify(authorService).saveAuthor(any());
    }


    @DisplayName("Удаление автора")
    @Order(4)
    @Test
    void deleteAuthorTest() throws Exception {
        final String id = "43657fdg";
        mvc.perform(delete("/api/author/" + id))
                .andExpect(status().isOk());
        verify(authorService).deleteAuthor(id);
    }
}