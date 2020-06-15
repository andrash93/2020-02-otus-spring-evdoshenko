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
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.library.data.model.Author;
import ru.otus.library.data.model.Book;
import ru.otus.library.data.model.Genre;
import ru.otus.library.repository.AuthorRepository;
import ru.otus.library.repository.BookRepository;
import ru.otus.library.repository.GenreRepository;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DisplayName("Тест GenreController")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@WebFluxTest(GenreController.class)
class GenreControllerTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private GenreRepository genreRepository;


    @DisplayName("Список жанров")
    @Order(0)
    @Test
    void getAllGenreTest() {
        Genre[] genres = new Genre[1];
        Genre genre = new Genre();
        genre.setId("f45");
        genre.setName("жанр");

        genres[0] = genre;

        when(genreRepository.findAll()).thenReturn(Flux.just(genres));
        EntityExchangeResult<byte[]> result = webClient.get().uri("/api/genre/list")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType("application/json")
                .expectBody().returnResult();
        String content = new String(result.getResponseBody(), StandardCharsets.UTF_8);
        assertThat(content)
                .startsWith("[{").endsWith("}]")
                .contains("\"id\":", "\"name\":")
                .contains(genres[0].getId(), genres[0].getName());
    }


    @DisplayName("Получение жанра по ID")
    @Order(1)
    @Test
    void getGenreByIdTest() {
        String id = "454545";
        Genre genre = new Genre();
        genre.setId(id);
        genre.setName("жанра");

        when(genreRepository.findById(id)).thenReturn(Mono.just(genre));
        EntityExchangeResult<byte[]> result = webClient.get().uri("/api/genre/" + id)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType("application/json")
                .expectBody().returnResult();
        String content = new String(result.getResponseBody(), StandardCharsets.UTF_8);
        assertThat(content)
                .startsWith("{").endsWith("}")
                .contains("\"id\":", "\"name\":")
                .contains(genre.getId(), genre.getName());
    }


    @DisplayName("Добавление жанра")
    @Order(2)
    @Test
    void addGenre() {
        String id = "454545";
        Genre genre = new Genre();
        genre.setName(id);

        String genreDto = "{ \"name\": \"" + genre.getName() + "\"}";

        when(genreRepository.insert(any(Genre.class))).thenReturn(Mono.just(genre));
        webClient.post().uri("/api/genre")
                .contentType(MediaType.APPLICATION_JSON)            // ID == null
                .accept(MediaType.APPLICATION_JSON)
                .acceptCharset(StandardCharsets.UTF_8)
                .body(BodyInserters.fromValue(genreDto))
                .exchange()
                .expectStatus().isOk();
    }


    @DisplayName("Изменение жанра")
    @Order(3)
    @Test
    void updateGenre() {
        String id = "454545";
        Genre genre = new Genre();
        genre.setName(id);

        String genreDto = "{ \"id\": \"" + genre.getId() + "\",\"name\": \"" + genre.getName() + "\"}";
        when(genreRepository.findById(any(String.class))).thenReturn(Mono.just(genre));
        when(genreRepository.save(any(Genre.class))).thenReturn(Mono.just(genre));
        webClient.put().uri("/api/genre/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .acceptCharset(StandardCharsets.UTF_8)
                .body(BodyInserters.fromValue(genreDto))
                .exchange()
                .expectStatus().isOk();
    }


    @DisplayName("Удаление жанра")
    @Order(4)
    @Test
    void deleteGenre() {
        String id = "454545";
        Genre genre = new Genre();
        genre.setName(id);


        when(genreRepository.findById(any(String.class))).thenReturn(Mono.just(genre));
        when(genreRepository.delete(any(Genre.class))).thenReturn(Mono.empty());
        webClient.delete().uri("/api/genre/" + id)
                .exchange()
                .expectStatus().isOk();
    }

}