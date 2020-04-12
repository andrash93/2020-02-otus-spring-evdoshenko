package ru.otus.library.dao.jdbc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.library.model.Author;
import ru.otus.library.model.Book;
import ru.otus.library.model.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("dao книг")
@JdbcTest
@Import(BookDaoJdbc.class)
class BookDaoJdbcTest {

    @Autowired
    private BookDaoJdbc dao;

    @DisplayName("Добавление книги")
    @Test
    void addBookTest() {
        Author author = new Author();
        author.setId(1L);
        Genre genre = new Genre();
        genre.setId(1L);

        Book book = new Book();
        book.setName("книга тест");
        book.setAuthor(author);
        book.setGenre(genre);
        book.setPublishing("Издательство тест");
        book.setQuantityPages(12);

        Optional<Book> addBook = dao.addBook(book);
        assertThat(true).isEqualTo(addBook.isPresent());

        Optional<Book> bookById = dao.findBookById(addBook.get().getId());
        assertThat(true).isEqualTo(bookById.isPresent());
    }

    @DisplayName("Добавление книги, если нет автора с таким id")
    @Test
    void addBookWithNotAuthorInDbTest() {
        Author author = new Author();
        author.setId(4L);
        Genre genre = new Genre();
        genre.setId(1L);

        Book book = new Book();
        book.setName("книга тест");
        book.setAuthor(author);
        book.setGenre(genre);
        book.setPublishing("Издательство тест");
        book.setQuantityPages(12);

        Optional<Book> addBook = dao.addBook(book);
        assertThat(false).isEqualTo(addBook.isPresent());
    }

    @DisplayName("Поиск книг по имени")
    @Test
    void findBookByNameTest() {
        List<Book> books = dao.findBooksByName("книга тест1");
        assertThat(1).isEqualTo(books.size());
    }

    @DisplayName("Удаление книги")
    @Test
    void deleteBookTest() {
        Author author = new Author();
        author.setId(1L);
        Genre genre = new Genre();
        genre.setId(1L);

        Book book = new Book();
        book.setName("книга тест");
        book.setAuthor(author);
        book.setGenre(genre);
        book.setPublishing("Издательство тест");
        book.setQuantityPages(12);

        Optional<Book> addBook = dao.addBook(book);
        assertThat(true).isEqualTo(addBook.isPresent());

        boolean isDelete = dao.deleteBook(addBook.get().getId());
        assertThat(true).isEqualTo(isDelete);

        Optional<Book> bookById = dao.findBookById(addBook.get().getId());
        assertThat(false).isEqualTo(bookById.isPresent());
    }

    @DisplayName("Поиск всех книг")
    @Test
    void getAllAuthorsTest() {
        List<Book> books = dao.getAllBooks();
        assertThat(3).isEqualTo(books.size());
    }

    @DisplayName("Поиск книг по автору")
    @Test
    void findBooksByAuthorTest() {
        List<Book> books = dao.findBooksByAuthor("автор тест2");
        assertThat(0).isEqualTo(books.size());

        books = dao.findBooksByAuthor("автор тест1");
        assertThat(2).isEqualTo(books.size());

    }

    @DisplayName("Количество книг")
    @Test
    void countAuthorTest() {
        Integer countBooks = dao.countBooks();
        assertThat(3).isEqualTo(countBooks);
    }

    @DisplayName("Обновление книги")
    @Test
    void updateBook() {
        Author author = new Author();
        author.setId(1L);
        Genre genre = new Genre();
        genre.setId(1L);

        Book book = new Book();
        book.setName("книга обновленная книга");
        book.setAuthor(author);
        book.setGenre(genre);
        book.setPublishing("Издательство обновлено");
        book.setQuantityPages(12);

        Optional<Book> updateBook = dao.updateBook(book, 1L);
        assertThat(true).isEqualTo(updateBook.isPresent());

        Optional<Book> bookById = dao.findBookById(1L);
        assertThat(true).isEqualTo(bookById.isPresent());

        assertThat("книга обновленная книга").isEqualTo(bookById.get().getName());
        assertThat("Издательство обновлено").isEqualTo(bookById.get().getPublishing());
    }
}