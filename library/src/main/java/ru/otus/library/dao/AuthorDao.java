package ru.otus.library.dao;

import ru.otus.library.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {

    Optional<Author> addAuthor(Author author);

    Optional<Author> findAuthorById(Long authorId);

    List<Author> findAuthorsByName(String authorName);

    List<Author> getAllAuthors();

    boolean deleteAuthor(Long authorId);

    Integer countAuthors();

}
