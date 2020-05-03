package ru.otus.library.service.impl;

import ru.otus.library.model.Genre;

import java.util.List;

public interface GenreService {

    void saveGenre(Genre genre);

    List<Genre> findGenreByName(String name);

    List<Genre> findAllGenre();

    void deleteGenre(Long id);
}
