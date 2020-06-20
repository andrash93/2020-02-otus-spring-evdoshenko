package ru.otus.library.repository;


import org.springframework.data.repository.CrudRepository;
import ru.otus.library.data.library.model.Book;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Long> {

    List<Book> findAll();

    List<Book> findByName(String name);

}

