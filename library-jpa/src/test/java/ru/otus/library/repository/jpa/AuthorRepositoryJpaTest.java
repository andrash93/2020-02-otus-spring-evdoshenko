package ru.otus.library.repository.jpa;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.library.model.Author;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Репозиторий на основе Jpa для работы авторами")
@DataJpaTest
@Import(AuthorRepositoryJpa.class)
class AuthorRepositoryJpaTest {

    @Autowired
    private AuthorRepositoryJpa repositoryJpa;


    @DisplayName("Сохранение автора")
    @Test
    void saveAuthorTest() {
        Author author = new Author();
        author.setName("Тест имя");
        author.setCountry("Тест страна");

        Long addAuthorId = repositoryJpa.saveAuthor(author);
        assertNotNull(addAuthorId);

        Optional<Author> authorById = repositoryJpa.findAuthorById(addAuthorId);
        assertTrue(authorById.isPresent());

        assertEquals(author.getName(), authorById.get().getName());
        assertEquals(author.getCountry(), authorById.get().getCountry());
    }

    @DisplayName("Поиск авторов по имени")
    @Test
    void findAuthorByNameTest() {
        Author author = new Author();
        author.setName("Тест имя");
        author.setCountry("Тест страна");

        Long addAuthorId = repositoryJpa.saveAuthor(author);
        assertNotNull(addAuthorId);

        List<Author> authors = repositoryJpa.findAuthorsByName("Тест имя");
        assertThat(1).isEqualTo(authors.size());
    }

    @DisplayName("Удаление автора")
    @Test
    void deleteAuthorTest() {

        Long authorId = 1L;
        repositoryJpa.deleteAuthor(authorId);

        Optional<Author> authorById = repositoryJpa.findAuthorById(authorId);
        assertThat(false).isEqualTo(authorById.isPresent());
    }

    @DisplayName("Поиск всех авторов")
    @Test
    void getAllAuthorsTest() {
        List<Author> authors = repositoryJpa.getAllAuthors();
        assertThat(3).isEqualTo(authors.size());
    }


}