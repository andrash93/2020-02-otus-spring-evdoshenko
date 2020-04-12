package ru.otus.library.dao;

import ru.otus.library.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreDao {

    Optional<Genre> addGenre(Genre genre);

    Optional<Genre> findGenreById(Long id);

    Optional<Genre> findGenreByName(String name);

    List<Genre> getAllGenres();

    boolean deleteGenre(Long genreId);

    Integer countGenres();

}
