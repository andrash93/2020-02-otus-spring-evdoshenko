package ru.otus.library.dao.jdbc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.library.model.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@DisplayName("dao жанров")
@JdbcTest
@Import(GenreDaoJdbc.class)
class GenreDaoJdbcTest {

    @Autowired
    private GenreDaoJdbc dao;

    @DisplayName("Добавление жанра")
    @Test
    void addGenreTest() {
        Genre genre = new Genre();
        genre.setName("Тест жанр");

        Optional<Genre> addGenre = dao.addGenre(genre);
        assertThat(addGenre.isPresent()).isEqualTo(true);

        Optional<Genre> genreById = dao.findGenreById(addGenre.get().getId());
        assertTrue(genreById.isPresent());

        assertEquals(genre.getName(), genreById.get().getName());
    }

    @DisplayName("Количество жанров")
    @Test
    void countGenresTest() {
        Integer countGenres = dao.countGenres();
        assertThat(2).isEqualTo(countGenres);
    }

    @DisplayName("Поиск жанров по названию")
    @Test
    void findGenresByNameTest() {
        Genre genre = new Genre();
        genre.setName("Тест жанр");

        Optional<Genre> addGenre = dao.addGenre(genre);
        assertThat(addGenre.isPresent()).isEqualTo(true);

        Optional<Genre> genres = dao.findGenreByName("Тест жанр");
        assertThat(genres.isPresent()).isEqualTo(true);
    }

    @DisplayName("Удаление жанра")
    @Test
    void deleteAuthorTest() {
        Genre genre = new Genre();
        genre.setName("Тест жанр");

        Optional<Genre> addGenre = dao.addGenre(genre);
        assertTrue(addGenre.isPresent());

        boolean isDelete = dao.deleteGenre(addGenre.get().getId());
        assertThat(true).isEqualTo(isDelete);

        Optional<Genre> genreById = dao.findGenreById(addGenre.get().getId());
        assertThat(false).isEqualTo(genreById.isPresent());
    }

    @DisplayName("Поиск всех авторов")
    @Test
    void getAllAuthorsTest() {

        List<Genre> allGenres = dao.getAllGenres();
        assertThat(2).isEqualTo(allGenres.size());
    }
}