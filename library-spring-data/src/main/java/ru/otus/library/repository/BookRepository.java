package ru.otus.library.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.library.model.Book;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Long> {

    List<Book> findAll();

    List<Book> findByName(String name);

    List<Book> findByAuthor_Name(String name);

    List<Book> findByGenre_Name(String name);
}
