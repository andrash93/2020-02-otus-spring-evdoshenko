package ru.otus.library.dao;

import ru.otus.library.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {

    Optional<Book> addBook(Book book);

    Optional<Book> updateBook(Book book, Long id);

    Optional<Book> findBookById(Long id);

    List<Book> findBooksByName(String name);

    List<Book> findBooksByAuthor(String authorName);

    List<Book> findBooksByGenre(String genreName);

    List<Book> getAllBooks();

    boolean deleteBook(Long id);

    Integer countBooks();

}
