package ru.otus.library.facade;

public interface BookFacade {

    void addBook();

    void findBookByName(String name);

    void findBooksByAuthor(String authorName);

    void findAllBooks();

    void deleteBook(Long id);

    void updateBook(Long id);

}
