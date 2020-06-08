package ru.otus.library.repository;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.library.data.model.Author;
import ru.otus.library.data.model.Genre;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тест AuthorRepository ")
@DataMongoTest
@ComponentScan({"ru.otus.library.changelog"})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthorRepositoryTest {


    @Autowired
    private AuthorRepository authorRepository;

    @Test
    @Order(2)
    @DisplayName("Получение авторов по ids")
    void getAuthorByIds() {
        List<Author> authors = authorRepository.findAll();
        assertEquals(authors.size(),3);
        List<String> ids = new ArrayList<>();
        ids.add(authors.get(0).getId());
        ids.add(authors.get(1).getId());
        List<Author> authorByIdIn = authorRepository.findAuthorByIdIn(ids);
        assertEquals(authorByIdIn.size(),2);
    }

    @Test
    @Order(3)
    @DisplayName("Получение автора по имени")
    void getAuthorByName() {
        List<Author> authors = authorRepository.findByName("Беккет");
        assertEquals(authors.size(),1);
    }


    private String addAuthor(MongoTemplate template, String name, String country) {
        Author author = new Author();
        author.setName(name);
        author.setCountry(country);
        Author save = template.save(author);
        return save.getId();
    }


}