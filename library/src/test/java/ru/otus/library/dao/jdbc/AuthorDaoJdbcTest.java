package ru.otus.library.dao.jdbc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.library.model.Author;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@DisplayName("dao авторов")
@JdbcTest
@Import(AuthorDaoJdbc.class)
class AuthorDaoJdbcTest {

    @Autowired
    private AuthorDaoJdbc dao;

    @DisplayName("Добавление автора")
    @Test
    void addAuthorTest() {
        Author author = new Author();
        author.setName("Тест имя");
        author.setCountry("Тест страна");

        Optional<Author> addAuthor = dao.addAuthor(author);
        assertTrue(addAuthor.isPresent());

        Optional<Author> authorById = dao.findAuthorById(addAuthor.get().getId());
        assertTrue(authorById.isPresent());

        assertEquals(author.getName(), authorById.get().getName());
        assertEquals(author.getCountry(), authorById.get().getCountry());
    }

    @DisplayName("Количество авторов")
    @Test
    void countAuthorTest() {
        Integer countAuthors = dao.countAuthors();
        assertThat(3).isEqualTo(countAuthors);
    }

    @DisplayName("Поиск авторов по имени")
    @Test
    void findAuthorByNameTest() {
        Author author = new Author();
        author.setName("Тест имя");
        author.setCountry("Тест страна");

        Optional<Author> addAuthor = dao.addAuthor(author);
        assertTrue(addAuthor.isPresent());

        List<Author> authors = dao.findAuthorsByName("Тест имя");
        assertThat(1).isEqualTo(authors.size());
    }

    @DisplayName("Удаление автора")
    @Test
    void deleteAuthorTest() {
        Author author = new Author();
        author.setName("Тест имя");
        author.setCountry("Тест страна");

        Optional<Author> addAuthor = dao.addAuthor(author);
        assertTrue(addAuthor.isPresent());

        boolean isDelete = dao.deleteAuthor(addAuthor.get().getId());
        assertThat(true).isEqualTo(isDelete);

        Optional<Author> authorById = dao.findAuthorById(addAuthor.get().getId());

        assertThat(false).isEqualTo(authorById.isPresent());
    }

    @DisplayName("Удаление автора по которму есть книги")
    @Test
    void deleteAuthorWithBooksTest() {

        List<Author> authors = dao.findAuthorsByName("автор тест1");
        boolean isDelete = dao.deleteAuthor(authors.get(0).getId());
        assertThat(true).isEqualTo(isDelete);

        Optional<Author> authorById = dao.findAuthorById(authors.get(0).getId());

        assertThat(false).isEqualTo(authorById.isPresent());
    }

    @DisplayName("Поиск всех авторов")
    @Test
    void getAllAuthorsTest() {
        List<Author> authors = dao.getAllAuthors();
        assertThat(3).isEqualTo(authors.size());
    }


}