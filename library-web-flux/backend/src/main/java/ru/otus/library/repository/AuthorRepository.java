package ru.otus.library.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import ru.otus.library.data.model.Author;

public interface AuthorRepository extends ReactiveMongoRepository<Author, String> {

    Flux<Author> findByName(String name);

}
