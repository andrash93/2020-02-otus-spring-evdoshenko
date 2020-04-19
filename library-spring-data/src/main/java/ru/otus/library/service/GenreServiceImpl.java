package ru.otus.library.service;

import org.springframework.stereotype.Service;
import ru.otus.library.model.Genre;
import ru.otus.library.repository.GenreRepository;

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
        genreRepository.save(genre);
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
    public void deleteGenre(Long id) {
        genreRepository.deleteById(id);
    }
}
