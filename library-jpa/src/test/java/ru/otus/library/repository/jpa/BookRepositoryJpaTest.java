package ru.otus.library.repository.jpa;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.library.model.Author;
import ru.otus.library.model.Book;
import ru.otus.library.model.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Репозиторий на основе Jpa для работы с книгами")
@DataJpaTest
@Import(BookRepositoryJpa.class)
class BookRepositoryJpaTest {


    @Autowired
    private BookRepositoryJpa repositoryJpa;

    @DisplayName("Сохранение книги")
    @Test
    void saveBookTest() {
        Book book = new Book();
        book.setName("Цныгааа");
        book.setPublishing("Давай");
        book.setQuantityPages(1);

        Author author1 = new Author();
        author1.setName("Првет111");
        author1.setCountry("Давай111");

        Genre genre = new Genre();
        genre.setId(1L);

        Author author = new Author();
        author.setId(1L);
        author.setName("Тест имя");
        author.setCountry("Тест страна");

        book.setAuthor(author);
        book.setGenre(genre);
        Long bookId = repositoryJpa.saveBook(book);

        assertNotNull(bookId);

        Optional<Book> bookById = repositoryJpa.findBookById(bookId);
        assertTrue(bookById.isPresent());

        assertEquals(book.getName(), bookById.get().getName());

    }


    @DisplayName("Поиск книг по имени")
    @Test
    void findAuthorByNameTest() {
        List<Book> books = repositoryJpa.findBooksByName("книга3");
        assertThat(1).isEqualTo(books.size());
    }

    @DisplayName("Удаление книги")
    @Test
    void deleteBookTest() {

        Long bookId = 1L;
        repositoryJpa.deleteBook(bookId);

        Optional<Book> bookById = repositoryJpa.findBookById(bookId);
        assertThat(false).isEqualTo(bookById.isPresent());
    }

    @DisplayName("Поиск всех книг")
    @Test
    void getAllBooksTest() {
        List<Book> books = repositoryJpa.getAllBooks();
        assertThat(3).isEqualTo(books.size());
    }


    @DisplayName("Редактирование книги")
    @Test
    void updateBookTest() {

        Book book = new Book();
        book.setName("Цныгааа");
        book.setPublishing("Да");
        book.setQuantityPages(1);
        book.setId(1L);

        Genre genre = new Genre();
        genre.setId(1L);

        Author author = new Author();
        author.setId(1L);
        author.setName("Тест имя");
        author.setCountry("Тест страна");

        book.setAuthor(author);
        book.setGenre(genre);
        repositoryJpa.saveBook(book);

        Optional<Book> bookById = repositoryJpa.findBookById(1L);
        assertTrue(bookById.isPresent());

        assertEquals(book.getName(), bookById.get().getName());
    }


}