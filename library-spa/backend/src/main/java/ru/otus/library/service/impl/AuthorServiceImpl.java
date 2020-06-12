package ru.otus.library.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.library.data.model.Author;
import ru.otus.library.repository.AuthorRepository;
import ru.otus.library.service.AuthorService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public void saveAuthor(Author author) {
        authorRepository.insert(author);
    }

    @Override
    public void updateAuthor(String id, Author author) {
        if (authorRepository.existsById(id)) {
            authorRepository.save(author);
        }
    }

    @Override
    public List<Author> findAuthorsByName(String name) {
        if (name == null || name.equals("")) {
            return Collections.EMPTY_LIST;
        }
        return authorRepository.findByName(name);
    }

    @Override
    public List<Author> findAllAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public void deleteAuthor(String id) {
        authorRepository.deleteById(id);
    }

    @Override
    public Optional<Author> getAuthorById(String id) {
        return authorRepository.findById(id);
    }

    @Override
    public List<Author> getAuthorByIds(List<String> ids) {
        return authorRepository.findAuthorByIdIn(ids);
    }
}
