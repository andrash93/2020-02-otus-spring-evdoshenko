package ru.otus.library.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.library.model.Genre;
import ru.otus.library.repository.GenreRepository;
import ru.otus.library.service.GenreService;

import java.util.Collections;
import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public void saveGenre(Genre genre) {
        genreRepository.saveGenre(genre);
    }

    @Override
    public List<Genre> findGenreByName(String name) {
        if (name == null || name.equals("")) {
            return Collections.EMPTY_LIST;
        }
        return genreRepository.findGenresByName(name);
    }

    @Override
    public List<Genre> findAllGenre() {
        return genreRepository.getAllGenres();
    }

    @Override
    public void deleteGenre(Long id) {
        genreRepository.deleteGenre(id);
    }
}
