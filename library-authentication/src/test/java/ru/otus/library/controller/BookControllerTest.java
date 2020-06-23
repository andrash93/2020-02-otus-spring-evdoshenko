package ru.otus.library.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.otus.library.data.library.dto.BookDto;
import ru.otus.library.data.library.model.Book;
import ru.otus.library.service.library.AuthorService;
import ru.otus.library.service.library.BookService;
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
@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;
    @MockBean
    private AuthorService authorService;
    @MockBean
    private GenreService genreService;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @DisplayName("Список книг")
    @Order(0)
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void listBooksTest() throws Exception {
        List<BookDto> books = new ArrayList<>();
        BookDto bookDto = new BookDto();
        bookDto.setId(2L);
        bookDto.setName("книга");
        bookDto.setAuthor("автор");
        bookDto.setGenre("жанр");
        bookDto.setPage(45);
        bookDto.setPublishing("пубилка");
        books.add(bookDto);

        when(bookService.findAllBooks()).thenReturn(books);
        MvcResult result = mvc.perform(get("/book/list"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andReturn();
        String content = result.getResponse().getContentAsString();
        assertThat(content)
                .contains(books.get(0).getName());
    }


    @DisplayName("Добавление книги /book/add")
    @Test
    @Order(1)
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void addBookTest() throws Exception {
        mvc.perform(get("/book/add"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"));
    }


    @DisplayName("Добавлене книги /book/edit")
    @Order(3)
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void insertBookTest() throws Exception {
        mvc.perform(
                post("/book/edit")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                        .param("authorId", "346") //id = null
                        .param("genreId", "644")
                        .param("name", "name")
                        .param("publishing", "publishing")
                        .param("page", "34")
        )
                .andExpect(status().is3xxRedirection());
        ArgumentCaptor<Book> captor = ArgumentCaptor.forClass(Book.class);
        verify(bookService).saveBook(captor.capture());
    }

    @DisplayName("Редактирование книги")
    @Order(3)
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void updateBookTest() throws Exception {
        mvc.perform(
                post("/book/edit")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                        .param("bookId", "3")
                        .param("authorId", "346")
                        .param("genreId", "6446")
                        .param("name", "name")
                        .param("publishing", "publishing")
                        .param("page", "34")
        )
                .andExpect(status().is3xxRedirection());
        ArgumentCaptor<Book> captor = ArgumentCaptor.forClass(Book.class);
        verify(bookService).updateBook(captor.capture());
    }


    @DisplayName("Удаление книги")
    @Order(5)
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void deleteAuthor() throws Exception {
        mvc.perform(
                get("/book/delete")
                        .param("id", "3"))
                .andExpect(status().is3xxRedirection());
        verify(bookService).deleteBook(anyLong());
    }
}