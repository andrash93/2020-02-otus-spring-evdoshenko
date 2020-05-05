package ru.otus.library.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.library.model.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    @DisplayName("Поиск авторов по имени")
    @Test
    void findAuthorByNameTest() {
        Author author = new Author();
        author.setName("Тест имя");
        author.setCountry("Тест страна");

        authorRepository.save(author);

        List<Author> authors = authorRepository.findByName("Тест имя");
        assertThat(1).isEqualTo(authors.size());
    }

}