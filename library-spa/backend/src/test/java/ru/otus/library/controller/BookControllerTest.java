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
import ru.otus.library.data.dto.BookDto;
import ru.otus.library.data.model.Book;
import ru.otus.library.service.BookService;

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
@WebMvcTest(BookController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;


    @DisplayName("Список книг")
    @Order(0)
    @Test
    void getAllBooksTest() throws Exception {
        List<BookDto> bookList = new ArrayList<>();

        BookDto book = new BookDto();
        book.setId("234");
        book.setName("имя 21");
        book.setAuthor("автор");
        book.setGenre("жанр");
        bookList.add(book);

        book = new BookDto();
        book.setId("234");
        book.setName("имя 21");
        book.setAuthor("автор");
        book.setGenre("жанр");
        bookList.add(book);


        when(bookService.findAllBooks()).thenReturn(bookList);
        MvcResult result = mvc.perform(get("/api/book/list"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andReturn();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        assertThat(content)
                .startsWith("[{").endsWith("}]")
                .contains("\"id\":", "\"name\":", "\"author\":")
                .contains(bookList.get(0).getId(),
                        bookList.get(0).getName(), bookList.get(0).getAuthor(),
                        bookList.get(1).getName(), bookList.get(1).getAuthor());
    }

    @DisplayName("Поиск книги по назвнию")
    @Order(0)
    @Test
    void findBooksByNameTest() throws Exception {
        String name = "название";
        List<BookDto> bookList = new ArrayList<>();

        BookDto book = new BookDto();
        book.setId("234");
        book.setName("имя 21");
        book.setAuthor("автор");
        book.setGenre("жанр");
        bookList.add(book);

        book = new BookDto();
        book.setId("234");
        book.setName("имя 21");
        book.setAuthor("автор");
        book.setGenre("жанр");
        bookList.add(book);

        when(bookService.findBooksByName(name)).thenReturn(bookList);
        MvcResult result = mvc.perform(get("/api/book/find/name?name=" + name))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andReturn();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        assertThat(content)
                .startsWith("[{").endsWith("}]")
                .contains("\"id\":", "\"name\":", "\"author\":")
                .contains(bookList.get(0).getId(),
                        bookList.get(0).getName(), bookList.get(0).getAuthor(),
                        bookList.get(1).getName(), bookList.get(1).getAuthor());
    }

    @DisplayName("Получение книги по ID")
    @Order(1)
    @Test
    void getBookByIdTest() throws Exception {
        final String id = "43657fdg";
        Book book = new Book();
        book.setId(id);
        book.setName("имя 21");
        book.setAuthorId("345dfg");


        when(bookService.getBookById(id)).thenReturn(Optional.of(book));
        MvcResult result = mvc.perform(get("/api/book/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andReturn();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        assertThat(content)
                .startsWith("{").endsWith("}")
                .contains("\"id\":", "\"name\":", "\"authorId\":")
                .contains(book.getId(),
                        book.getName(), book.getAuthorId());
    }

    @DisplayName("Добавление книги")
    @Order(2)
    @Test
    void addBookTest() throws Exception {
        String bookDto = "{\"name\": \"книга\"," +
                "\"authorId\": \"dfg345\"," +
                "\"genreId\": \"dffgfg345\"" +
                "}";
        mvc.perform(post("/api/book").content(bookDto)
                .contentType("application/json")
                .accept("application/json"))
                .andExpect(status().isOk());
        verify(bookService).saveBook(any());
    }


    @DisplayName("Удаление книги")
    @Order(4)
    @Test
    void deleteBookTest() throws Exception {
        final String id = "43657fdg";
        mvc.perform(delete("/api/book/" + id))
                .andExpect(status().isOk());
        verify(bookService).deleteBook(id);
    }
}