package ru.otus.library.facade;

public interface AuthorFacade {

    void addAuthor();

    void findAuthorByName(String authorName);

    void findAllAuthors();

    void deleteAuthor(String id);

}
