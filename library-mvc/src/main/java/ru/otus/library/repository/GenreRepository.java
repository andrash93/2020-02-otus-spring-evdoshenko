package ru.otus.library.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.library.data.model.Genre;
import java.util.List;

public interface GenreRepository extends MongoRepository<Genre, String> {

    List<Genre> findAll();

    List<Genre> findGenreByIdIn(List<String> ids);
}
