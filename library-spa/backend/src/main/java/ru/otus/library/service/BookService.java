package ru.otus.library.service;



import ru.otus.library.data.dto.BookDto;
import ru.otus.library.data.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    void saveBook(Book book);

    Optional<Book> getBookById(String id);

    List<BookDto> findBooksByName(String name);

    List<BookDto> findBooksByAuthor(String authorId);

    List<BookDto> findAllBooks();

    void deleteBook(String id);

    void deleteBooksByAuthorId(String id);

    void deleteBooksByGenreId(String id);

    void updateBook(String id, Book book);
}
