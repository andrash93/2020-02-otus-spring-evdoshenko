package ru.otus.library.repository;

import ru.otus.library.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {

    Long saveBook(Book book);

    Optional<Book> findBookById(Long id);

    List<Book> findBooksByName(String name);

    List<Book> getAllBooks();

    void deleteBook(Long id);

}
