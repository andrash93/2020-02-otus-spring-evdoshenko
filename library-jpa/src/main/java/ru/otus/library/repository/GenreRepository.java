package ru.otus.library.repository;

import ru.otus.library.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {

    Long saveGenre(Genre genre);

    Optional<Genre> findGenreById(Long id);

    List<Genre> findGenresByName(String genreName);

    List<Genre> getAllGenres();

    void deleteGenre(Long genreId);

}
