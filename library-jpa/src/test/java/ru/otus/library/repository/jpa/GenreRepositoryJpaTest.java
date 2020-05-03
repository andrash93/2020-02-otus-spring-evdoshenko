package ru.otus.library.repository.jpa;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.library.model.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Репозиторий на основе Jpa для работы жанрами")
@DataJpaTest
@Import(GenreRepositoryJpa.class)
class GenreRepositoryJpaTest {

    @Autowired
    private GenreRepositoryJpa repositoryJpa;


    @DisplayName("Сохранение жанра")
    @Test
    void saveGenreTest() {
        Genre genre = new Genre();
        genre.setName("жаа");

        Long addGenreId = repositoryJpa.saveGenre(genre);
        assertNotNull(addGenreId);

        Optional<Genre> genreById = repositoryJpa.findGenreById(addGenreId);
        assertTrue(genreById.isPresent());

        assertEquals(genre.getName(), genreById.get().getName());
    }


    @DisplayName("Удаление жанра")
    @Test
    void deleteGenreTest() {

        Long genreId = 1L;
        repositoryJpa.deleteGenre(genreId);

        Optional<Genre> genreById = repositoryJpa.findGenreById(genreId);
        assertThat(false).isEqualTo(genreById.isPresent());
    }

    @DisplayName("Поиск всех жанров")
    @Test
    void getAllGenresTest() {
        List<Genre> genres = repositoryJpa.getAllGenres();
        assertThat(1).isEqualTo(genres.size());
    }

    @DisplayName("Поиск жанров по имени")
    @Test
    void findGenresByNameTest() {
        String genreName = "Тест имя";
        Genre genre = new Genre();
        genre.setName(genreName);

        Long addGenreId = repositoryJpa.saveGenre(genre);
        assertNotNull(addGenreId);

        List<Genre> genres = repositoryJpa.findGenresByName(genreName);
        assertThat(1).isEqualTo(genres.size());
    }

}