package ru.otus.library.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import ru.otus.library.data.model.Author;
import ru.otus.library.data.model.Book;

@DisplayName("Тест AuthorRepository")
@DataMongoTest
@ComponentScan({"ru.otus.library.changelog"})
class AuthorRepositoryTest {

    @Autowired
    AuthorRepository authorRepository;

    @Test
    @DisplayName("Поиск автора по имени")
    public void findAuthorByNameTest() {
        Flux<String> name = authorRepository.findByName("Пушкин")
                .map(Author::getName);
        StepVerifier
                .create(name)
                .expectNext("Пушкин")
                .expectComplete()
                .verify();
    }

}