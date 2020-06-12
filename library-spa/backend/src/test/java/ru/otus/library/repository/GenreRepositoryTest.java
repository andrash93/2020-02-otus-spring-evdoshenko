package ru.otus.library.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import ru.otus.library.data.model.Genre;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тест GenreRepository ")
@DataMongoTest
@ComponentScan({"ru.otus.library.changelog"})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GenreRepositoryTest {

    @Autowired
    private GenreRepository genreRepository;

    @Test
    @Order(2)
    @DisplayName("Получение жанров по ids")
    void getGenreByIds() {
        List<Genre> genres = genreRepository.findAll();
        assertEquals(genres.size(),3);
        List<String> ids = new ArrayList<>();
        ids.add(genres.get(0).getId());
        ids.add(genres.get(1).getId());
        List<Genre> genreByIdIn = genreRepository.findGenreByIdIn(ids);
        assertEquals(genreByIdIn.size(),2);
    }

}