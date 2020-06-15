package ru.otus.library.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.library.data.model.Book;


public interface BookRepository extends ReactiveMongoRepository<Book, String> {

    Flux<Book> findByName(String name);

    Mono<Void> removeBookByAuthor_Id(String authorId);

    Flux<Book> findBooksByAuthor_Id(String authorId);
}
