package ru.otus.library.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.library.data.model.Author;

import java.util.List;

public interface AuthorRepository extends MongoRepository<Author, String> {

    List<Author> findAll();

    List<Author> findByName(String name);

    List<Author> findAuthorByIdIn(List<String> ids);

}
