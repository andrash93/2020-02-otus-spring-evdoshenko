package ru.otus.library.service;

import ru.otus.library.model.Author;
import ru.otus.library.model.Book;
import ru.otus.library.model.Genre;

import java.util.List;
import java.util.Optional;

public interface LibraryService {

    void addAuthor(Author author);

    List<Author> findAuthorsByName(String name);

    List<Author> findAllAuthors();

    boolean deleteAuthor(Long id);

    void addGenre(Genre genre);

    Optional<Genre> findGenreByName(String name);

    List<Genre> findAllGenre();

    boolean deleteGenre(Long id);

    void addBook(Book book);

    List<Book> findBooksByName(String name);

    List<Book> findBooksByAuthor(String authorName);

    List<Book> findBooksByGenre(String genreName);

    List<Book> findAllBooks();

    boolean deleteBook(Long id);

    void updateBook(Book book, Long id);

}
