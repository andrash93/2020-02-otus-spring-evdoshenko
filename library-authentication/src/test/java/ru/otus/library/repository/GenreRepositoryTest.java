package ru.otus.library.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.library.data.library.model.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
class GenreRepositoryTest {

    @Autowired
    private GenreRepository genreRepository;

    @DisplayName("Поиск жанров по названию")
    @Test
    void findGenreByNameTest() {
        Genre genre = new Genre();
        genre.setName("Тест имя");

        genreRepository.save(genre);

        List<Genre> genres = genreRepository.findByName("Тест имя");
        assertThat(1).isEqualTo(genres.size());
    }

}