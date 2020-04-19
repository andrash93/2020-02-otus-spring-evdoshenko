package ru.otus.library.facade;

public interface LibraryFacade {

    void addBook();

    void findBookByName(String name);

    void findBooksByAuthor(String authorName);

    void findBooksByGenre(String genreName);

    void findAllBooks();

    void deleteBook(Long id);

    void updateBook(Long id);

    void addAuthor();

    void findAuthorByName(String authorName);

    void findAllAuthors();

    void deleteAuthor(Long id);

    void addGenre();

    void findGenreByName(String name);

    void findAllGenre();

    void deleteGenre(Long id);

}
