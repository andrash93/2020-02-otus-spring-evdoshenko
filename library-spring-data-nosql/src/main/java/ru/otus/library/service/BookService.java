package ru.otus.library.service;

import ru.otus.library.model.Book;

import java.util.List;

public interface BookService {

    void saveBook(Book book);

    List<Book> findBooksByName(String name);

    List<Book> findBooksByAuthor(String authorName);

    List<Book> findAllBooks();

    void deleteBook(String id);

    void deleteBooksByAuthorId(String id);

    void deleteBooksByGenreId(String id);

    void updateBook(Book book);
}
