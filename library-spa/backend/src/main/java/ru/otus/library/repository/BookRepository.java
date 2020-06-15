package ru.otus.library.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.library.data.model.Book;

import java.util.List;

public interface BookRepository extends MongoRepository<Book, String> {

    List<Book> findAll();

    List<Book> findByName(String name);

    void removeBookByAuthorId(String authorId);

    void removeBookByGenreId(String authorId);

    List<Book> findBooksByAuthorIdIn(List<String> ids);
}
