package ru.otus.library.service;

import ru.otus.library.data.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    void saveAuthor(Author author);

    void updateAuthor(Author author);

    List<Author> findAuthorsByName(String name);

    List<Author> findAllAuthors();

    void deleteAuthor(String id);

    Optional<Author> getAuthorById(String id);

    List<Author> getAuthorByIds(List<String> ids);
}
