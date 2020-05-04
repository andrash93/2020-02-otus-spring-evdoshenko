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
        genreRepository.insert(genre);
    }

    @Override
    public List<Genre> findGenreByName(String name) {
        if (name == null || name.equals("")) {
            return Collections.EMPTY_LIST;
        }
        return genreRepository.findByName(name);
    }

    @Override
    public List<Genre> findAllGenre() {
        return genreRepository.findAll();
    }

    @Override
    public void deleteGenre(String id) {
        genreRepository.deleteById(id);
    }
}
