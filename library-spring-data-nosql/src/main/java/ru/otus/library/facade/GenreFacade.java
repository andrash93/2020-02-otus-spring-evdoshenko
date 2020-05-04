package ru.otus.library.facade;

public interface GenreFacade {

    void addGenre();

    void findGenreByName(String name);

    void findAllGenre();

    void deleteGenre(String id);
}
