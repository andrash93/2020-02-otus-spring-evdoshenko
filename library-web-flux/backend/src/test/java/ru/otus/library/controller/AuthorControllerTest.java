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
import ru.otus.library.data.model.Author;
import ru.otus.library.data.model.Book;
import ru.otus.library.repository.AuthorRepository;
import ru.otus.library.repository.BookRepository;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Тест AuthorController")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@WebFluxTest(AuthorController.class)
class AuthorControllerTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private AuthorRepository authorRepository;
    @MockBean
    private BookRepository bookRepository;

    @DisplayName("Список авторов")
    @Order(0)
    @Test
    void getAllAuthorsTest() {
        Author[] authors = new Author[1];
        Author author = new Author();
        author.setId("454545");
        author.setName("Пушкин");
        author.setCountry("Россия");
        authors[0] = author;

        when(authorRepository.findAll()).thenReturn(Flux.just(authors));
        EntityExchangeResult<byte[]> result = webClient.get().uri("/api/author/list")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType("application/json")
                .expectBody().returnResult();
        String content = new String(result.getResponseBody(), StandardCharsets.UTF_8);
        assertThat(content)
                .startsWith("[{").endsWith("}]")
                .contains("\"id\":", "\"name\":", "\"country\":")
                .contains(authors[0].getId(), authors[0].getName(), authors[0].getCountry());
    }


    @DisplayName("Получение автора по ID")
    @Order(1)
    @Test
    void getAuthorByIdTest() {
        String id = "454545";
        Author author = new Author();
        author.setId(id);
        author.setName("Пушкин");
        author.setCountry("Россия");
        when(authorRepository.findById(id)).thenReturn(Mono.just(author));
        EntityExchangeResult<byte[]> result = webClient.get().uri("/api/author/" + id)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType("application/json")
                .expectBody().returnResult();
        String content = new String(result.getResponseBody(), StandardCharsets.UTF_8);
        assertThat(content)
                .startsWith("{").endsWith("}")
                .contains("\"id\":", "\"name\":", "\"country\":")
                .contains(author.getId(), author.getName(), author.getCountry());
    }


    @DisplayName("Поиск авторов по имени")
    @Order(0)
    @Test
    void findAuthorsByNameTest() throws Exception {
        String name = "имя 21";
        Author[] authors = new Author[2];

        Author author = new Author();
        author.setId("234");
        author.setName("имя 21");
        author.setCountry("Страна1");
        authors[0] = author;

        author = new Author();
        author.setId("234");
        author.setName("имя 21");
        author.setCountry("Страна1");
        authors[1] = author;


        when(authorRepository.findByName(name)).thenReturn(Flux.just(authors));

        EntityExchangeResult<byte[]> result = webClient.get().uri("/api/author/find?name=" + name)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType("application/json")
                .expectBody().returnResult();
        String content = new String(result.getResponseBody(), StandardCharsets.UTF_8);
        assertThat(content)
                .startsWith("[{").endsWith("}]")
                .contains("\"id\":", "\"name\":", "\"country\":")
                .contains(authors[0].getId(), authors[0].getName(), authors[0].getCountry());

    }



    @DisplayName("Добавление автора")
    @Order(2)
    @Test
    void addAuthor() {
        String id = "454545";
        Author author = new Author();
        author.setId(id);
        author.setName("Пушкин");
        author.setCountry("Россия");
        String authorDto = "{ \"name\": \"" + author.getName() + "\"," +
                "\"country\": \"" + author.getCountry() + "\"}";
        when(authorRepository.insert(any(Author.class))).thenReturn(Mono.just(author));
        webClient.post().uri("/api/author")
                .contentType(MediaType.APPLICATION_JSON)            // ID == null
                .accept(MediaType.APPLICATION_JSON)
                .acceptCharset(StandardCharsets.UTF_8)
                .body(BodyInserters.fromValue(authorDto))
                .exchange()
                .expectStatus().isOk();
    }


    @DisplayName("Изменение автора")
    @Order(3)
    @Test
    void updateAuthor() {
        String id = "454545";
        Author author = new Author();
        author.setId(id);
        author.setName("Пушкин");
        author.setCountry("Россия");
        String authorDto = "{ \"id\": \"" + author.getId() + "\",\"name\": \"" + author.getName() + "\"," +
                "\"country\": \"" + author.getCountry() + "\"}";
        when(authorRepository.findById(any(String.class))).thenReturn(Mono.just(author));
        when(authorRepository.save(any(Author.class))).thenReturn(Mono.just(author));
        webClient.put().uri("/api/author/"+id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .acceptCharset(StandardCharsets.UTF_8)
                .body(BodyInserters.fromValue(authorDto))
                .exchange()
                .expectStatus().isOk();
    }


    @DisplayName("Удаление автора")
    @Order(4)
    @Test
    void deleteAuthor() {
        String id = "454545";
        Author author = new Author();
        author.setId(id);
        author.setName("Пушкин");
        author.setCountry("Россия");

        Book book = new Book();
        book.setAuthor(author);

        when(authorRepository.findById(any(String.class))).thenReturn(Mono.just(author));
        when(authorRepository.delete(any(Author.class))).thenReturn(Mono.empty());
        when(bookRepository.removeBookByAuthor_Id(id)).thenReturn(Mono.empty());
        webClient.delete().uri("/api/author/" + id)
                .exchange()
                .expectStatus().isOk();
    }

}