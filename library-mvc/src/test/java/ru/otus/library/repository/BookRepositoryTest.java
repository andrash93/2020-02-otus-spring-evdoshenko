package ru.otus.library.repository;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import ru.otus.library.data.model.Author;
import ru.otus.library.data.model.Book;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тест BookRepository")
@DataMongoTest
@ComponentScan({"ru.otus.library.changelog"})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;

    @Test
    @Order(2)
    @DisplayName("Получение книги по названию")
    void getBookByName() {
        List<Book> books = bookRepository.findByName("Книга3");
        assertEquals(books.size(),1);
    }


    @Test
    @Order(23)
    @DisplayName("Получение книг по автор ids")
    void getBookByAuthorIds() {
        List<Author> authors = authorRepository.findAll();
        assertEquals(authors.size(),3);
        List<String> ids = new ArrayList<>();
        ids.add(authors.get(0).getId());
        ids.add(authors.get(1).getId());
        List<Book> booksByIds = bookRepository.findBooksByAuthorIdIn(ids);
        assertEquals(booksByIds.size(),2);
    }


    @Test
    @Order(42)
    @DisplayName("Удаление книги по автор id")
    void deleteBookByAuthorId() {
        List<Author> authors = authorRepository.findAll();
        assertEquals(authors.size(),3);

        List<String> ids = new ArrayList<>();
        ids.add(authors.get(0).getId());
        ids.add(authors.get(1).getId());

        bookRepository.removeBookByAuthorId(authors.get(0).getId());

        List<Book> booksByIds = bookRepository.findBooksByAuthorIdIn(ids);
        assertEquals(booksByIds.size(),1);
    }


}