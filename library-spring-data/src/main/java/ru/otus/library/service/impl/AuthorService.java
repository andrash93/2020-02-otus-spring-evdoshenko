package ru.otus.library.service.impl;

import ru.otus.library.model.Author;

import java.util.List;

public interface AuthorService {

    void saveAuthor(Author author);

    List<Author> findAuthorsByName(String name);

    List<Author> findAllAuthors();

    void deleteAuthor(Long id);
}
