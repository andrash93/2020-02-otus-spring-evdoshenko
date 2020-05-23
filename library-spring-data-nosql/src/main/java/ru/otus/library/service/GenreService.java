package ru.otus.library.service;

import ru.otus.library.model.Genre;

import java.util.List;

public interface GenreService {

    void saveGenre(Genre genre);

    List<Genre> findGenreByName(String name);

    List<Genre> findAllGenre();

    void deleteGenre(String id);

}
