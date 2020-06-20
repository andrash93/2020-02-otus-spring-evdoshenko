package ru.otus.library.repository;


import org.springframework.data.repository.CrudRepository;
import ru.otus.library.data.library.model.Author;

import java.util.List;

public interface AuthorRepository extends CrudRepository<Author, Long> {

    List<Author> findAll();

    List<Author> findByName(String s);

}
