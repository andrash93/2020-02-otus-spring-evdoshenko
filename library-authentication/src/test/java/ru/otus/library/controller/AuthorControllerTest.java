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
import ru.otus.library.data.library.model.Author;
import ru.otus.library.service.library.AuthorService;
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
@WebMvcTest(AuthorController.class)
class AuthorControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @DisplayName("Список авторов")
    @Order(0)
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void listAuthorsTest() throws Exception {
        List<Author> authors = new ArrayList<>();
        Author author = new Author();
        author.setAuthorId(3L);
        author.setName("автор1");
        author.setCountry("страна1");
        authors.add(author);

        when(authorService.findAllAuthors()).thenReturn(authors);
        MvcResult result = mvc.perform(get("/author/list"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andReturn();
        String content = result.getResponse().getContentAsString();
        assertThat(content)
                .contains(authors.get(0).getName());
    }


    @DisplayName("Добавление автора /author/add")
    @Test
    @Order(1)
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void addAuthorTest() throws Exception {
        mvc.perform(get("/author/add"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"));
    }


    @DisplayName("Добавлене автора /author/edit")
    @Order(3)
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void insertAuthorTest() throws Exception {
        mvc.perform(
                post("/author/edit")
                        .param("name", "автор1") // authorId = null
                        .param("county", "страна")

                       )
                .andExpect(status().is3xxRedirection());
        ArgumentCaptor<Author> captor = ArgumentCaptor.forClass(Author.class);
        verify(authorService).saveAuthor(captor.capture());
    }

    @DisplayName("Редактирование автора")
    @Order(3)
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void updateAuthorTest() throws Exception {
        mvc.perform(
                post("/author/edit")
                        .param("authorId", "3")
                        .param("name", "автор1")
                        .param("county", "страна")

                       )
                .andExpect(status().is3xxRedirection());
        ArgumentCaptor<Author> captor = ArgumentCaptor.forClass(Author.class);
        verify(authorService).updateAuthor(captor.capture());
    }


    @DisplayName("Удаление автора")
    @Order(5)
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void deleteAuthor() throws Exception {
        mvc.perform(
                get("/author/delete")
                        .param("id", "3"))
                .andExpect(status().is3xxRedirection());
        verify(authorService).deleteAuthor(anyLong());
    }
}