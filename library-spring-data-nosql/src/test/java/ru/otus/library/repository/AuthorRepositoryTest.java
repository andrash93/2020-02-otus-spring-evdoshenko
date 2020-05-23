package ru.otus.library.repository;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.library.model.Author;

import java.util.List;

import static org.junit.Assert.*;

@DataMongoTest
@ExtendWith(SpringExtension.class)
public class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    public void findByNameTest() {
        String name = "авто1";
        Author author = new Author();
        author.setName(name);
        authorRepository.save(author);
        List<Author> authors = authorRepository.findByName(name);
        assertEquals(authors.size(), 1);
    }

}