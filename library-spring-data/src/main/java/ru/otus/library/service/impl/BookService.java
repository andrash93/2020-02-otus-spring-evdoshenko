package ru.otus.library.service.impl;

import ru.otus.library.model.Book;

import java.util.List;

public interface BookService {

    void saveBook(Book book);

    List<Book> findBooksByName(String name);

    List<Book> findBooksByAuthor(String authorName);

    List<Book> findAllBooks();

    void deleteBook(Long id);

    void updateBook(Book book, Long id);

    boolean bookExist(Long id);

}
