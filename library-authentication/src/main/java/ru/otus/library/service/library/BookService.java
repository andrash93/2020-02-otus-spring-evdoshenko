package ru.otus.library.service.library;


import ru.otus.library.data.library.dto.BookDto;
import ru.otus.library.data.library.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    Optional<Book> getBookById(Long id);

    void saveBook(Book book);

    List<BookDto> findBooksByName(String name);

    List<BookDto> findAllBooks();

    void deleteBook(Long id);

    void updateBook(Book book);

}
