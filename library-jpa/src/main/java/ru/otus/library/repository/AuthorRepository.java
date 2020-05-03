package ru.otus.library.repository;

import ru.otus.library.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {

    Long saveAuthor(Author author);

    Optional<Author> findAuthorById(Long authorId);

    List<Author> findAuthorsByName(String authorName);

    List<Author> getAllAuthors();

    void deleteAuthor(Long authorId);

}
