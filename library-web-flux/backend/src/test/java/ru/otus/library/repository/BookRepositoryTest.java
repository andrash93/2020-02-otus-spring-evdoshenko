package ru.otus.library.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import ru.otus.library.data.model.Book;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тест BookRepository")
@DataMongoTest
@ComponentScan({"ru.otus.library.changelog"})
class BookRepositoryTest {

    @Autowired
    BookRepository bookRepository;

    @Test
    @DisplayName("Поиск книги по названию")
    public void findBookByNameTest() {
        Flux<String> bookName = bookRepository.findByName("Книга")
                .map(Book::getName);
        StepVerifier
                .create(bookName)
                .expectNext("Книга")
                .expectComplete()
                .verify();
    }

}