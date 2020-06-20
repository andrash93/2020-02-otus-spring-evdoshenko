package ru.otus.library.service.library;

import ru.otus.library.data.library.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    void saveAuthor(Author author);

    List<Author> findAuthorsByName(String name);

    List<Author> findAllAuthors();

    void deleteAuthor(Long id);

    Optional<Author> getAuthorById(Long id);

    void updateAuthor(Author author);
}
