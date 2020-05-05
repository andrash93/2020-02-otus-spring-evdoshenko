package ru.otus.library.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.library.model.Genre;

import java.util.List;

public interface GenreRepository extends CrudRepository<Genre, Long> {

    List<Genre> findAll();

    List<Genre> findByName(String name);

}
