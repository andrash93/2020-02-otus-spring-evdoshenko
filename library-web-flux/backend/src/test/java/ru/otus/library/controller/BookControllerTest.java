package ru.otus.library.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.library.data.dto.BookDto;
import ru.otus.library.data.model.Author;
import ru.otus.library.data.model.Book;
import ru.otus.library.data.model.Genre;
import ru.otus.library.repository.AuthorRepository;
import ru.otus.library.repository.BookRepository;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Тест BookRepository")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@WebFluxTest(BookController.class)
class BookControllerTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private BookRepository bookRepository;

    @DisplayName("Список книг")
    @Order(0)
    @Test
    void getAllBooksTest() {

        Book[] books = new Book[1];

        Book book = new Book();
        book.setId("dfgr6r");
        book.setName("книга");
        book.setPublishing("Издательство");
        Author author = new Author();
        author.setId("345");
        author.setName("Пушкин");
        author.setCountry("Россия");
        book.setAuthor(author);
        Genre genre = new Genre();
        genre.setId("456");
        genre.setName("жанр");
        book.setGenre(genre);
        books[0] = book;

        when(bookRepository.findAll()).thenReturn(Flux.just(books));
        EntityExchangeResult<byte[]> result = webClient.get().uri("/api/book/list")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType("application/json")
                .expectBody().returnResult();
        String content = new String(result.getResponseBody(), StandardCharsets.UTF_8);
        assertThat(content)
                .startsWith("[{").endsWith("}]")
                .contains("\"id\":", "\"name\":", "\"publishing\":")
                .contains(books[0].getId(), books[0].getName(), books[0].getPublishing());
    }



    @DisplayName("Поиск книги по назвнию")
    @Order(0)
    @Test
    void findBooksByNameTest() throws Exception {
        String name = "название";
        Book[] books = new Book[1];

        Book book = new Book();
        book.setId("dfgr6r");
        book.setName("книга");
        book.setPublishing("Издательство");
        Author author = new Author();
        author.setId("345");
        author.setName("Пушкин");
        author.setCountry("Россия");
        book.setAuthor(author);
        Genre genre = new Genre();
        genre.setId("456");
        genre.setName("жанр");
        book.setGenre(genre);
        books[0] = book;

        when(bookRepository.findByName(name)).thenReturn(Flux.just(books));
        EntityExchangeResult<byte[]> result = webClient.get().uri("/api/book/find/name?name=" + name)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType("application/json")
                .expectBody().returnResult();
        String content = new String(result.getResponseBody(), StandardCharsets.UTF_8);
        assertThat(content)
                .startsWith("[{").endsWith("}]")
                .contains("\"id\":", "\"name\":", "\"publishing\":")
                .contains(books[0].getId(), books[0].getName(), books[0].getPublishing());
    }


    @DisplayName("Получение книги по ID")
    @Order(1)
    @Test
    void getAuthorByIdTest() {
        String id = "454545";
        Book book = new Book();
        book.setId(id);
        book.setName("книга");
        book.setPublishing("Издательство");
        Author author = new Author();
        author.setId("345");
        author.setName("Пушкин");
        author.setCountry("Россия");
        book.setAuthor(author);
        Genre genre = new Genre();
        genre.setId("456");
        genre.setName("жанр");
        book.setGenre(genre);

        when(bookRepository.findById(id)).thenReturn(Mono.just(book));
        EntityExchangeResult<byte[]> result = webClient.get().uri("/api/book/" + id)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType("application/json")
                .expectBody().returnResult();
        String content = new String(result.getResponseBody(), StandardCharsets.UTF_8);
        assertThat(content)
                .startsWith("{").endsWith("}")
                .contains("\"id\":", "\"name\":", "\"publishing\":")
                .contains(book.getId(), book.getName(), book.getPublishing());
    }


    @DisplayName("Добавление книги")
    @Order(2)
    @Test
    void addBook() {
        String id = "454545";
        Book book = new Book();
        book.setId(id);
        book.setName("книга");
        book.setPublishing("Издательство");
        Author author = new Author();
        author.setId("345");
        author.setName("Пушкин");
        author.setCountry("Россия");
        book.setAuthor(author);
        Genre genre = new Genre();
        genre.setId("456");
        genre.setName("жанр");
        book.setGenre(genre);

        String bookDto = "{ \"name\": \"" + book.getName() + "\"," +
                "\"author\": \"" + book.getAuthor().getName() + "\"," +
                "\"publishing\": \"" + book.getPublishing() + "\"}";

        when(bookRepository.save(any(Book.class))).thenReturn(Mono.just(book));
        webClient.post().uri("/api/book")
                .contentType(MediaType.APPLICATION_JSON)            // ID == null
                .accept(MediaType.APPLICATION_JSON)
                .acceptCharset(StandardCharsets.UTF_8)
                .body(BodyInserters.fromValue(bookDto))
                .exchange()
                .expectStatus().isOk();
    }


    @DisplayName("Изменение книги")
    @Order(3)
    @Test
    void updateBook() {
        String id = "454545";
        Book book = new Book();
        book.setId(id);
        book.setName("книга");
        book.setPublishing("Издательство");
        Author author = new Author();
        author.setId("345");
        author.setName("Пушкин");
        author.setCountry("Россия");
        book.setAuthor(author);
        Genre genre = new Genre();
        genre.setId("456");
        genre.setName("жанр");
        book.setGenre(genre);

        String bookDto = "{ \"id\": \"" + book.getId() + "\", \"name\": \"" + book.getName() + "\"," +
                "\"author\": \"" + book.getAuthor().getName() + "\"," +
                "\"publishing\": \"" + book.getPublishing() + "\"}";

        when(bookRepository.findById(any(String.class))).thenReturn(Mono.just(book));
        when(bookRepository.save(any(Book.class))).thenReturn(Mono.just(book));
        webClient.put().uri("/api/book/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .acceptCharset(StandardCharsets.UTF_8)
                .body(BodyInserters.fromValue(bookDto))
                .exchange()
                .expectStatus().isOk();
    }


    @DisplayName("Удаление книги")
    @Order(4)
    @Test
    void deleteBook() {
        String id = "454545";
        Book book = new Book();
        book.setId(id);
        book.setName("книга");
        book.setPublishing("Издательство");
        Author author = new Author();
        author.setId("345");
        author.setName("Пушкин");
        author.setCountry("Россия");
        book.setAuthor(author);
        Genre genre = new Genre();
        genre.setId("456");
        genre.setName("жанр");
        book.setGenre(genre);

        when(bookRepository.findById(any(String.class))).thenReturn(Mono.just(book));
        when(bookRepository.delete(any(Book.class))).thenReturn(Mono.empty());
        webClient.delete().uri("/api/book/" + id)
                .exchange()
                .expectStatus().isOk();
    }

}