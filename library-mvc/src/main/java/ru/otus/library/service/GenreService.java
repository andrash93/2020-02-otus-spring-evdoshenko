package ru.otus.library.service;

import ru.otus.library.data.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreService {

    void saveGenre(Genre genre);

    void updateGenre(Genre genre);

    List<Genre> findAllGenre();

    Optional<Genre> getGenreById(String id);

    List<Genre> getGenreByIds(List<String> ids);

    void deleteGenre(String id);

}
