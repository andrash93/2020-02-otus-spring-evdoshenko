package ru.otus.library.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.library.data.model.Genre;
import ru.otus.library.repository.GenreRepository;
import ru.otus.library.service.GenreService;

import java.util.List;
import java.util.Optional;

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
    public void updateGenre(String id, Genre genre) {
        if (genreRepository.existsById(id)) {
            genreRepository.save(genre);
        }
    }

    @Override
    public List<Genre> findAllGenre() {
        return genreRepository.findAll();
    }

    @Override
    public Optional<Genre> getGenreById(String id) {
        return genreRepository.findById(id);
    }

    @Override
    public List<Genre> getGenreByIds(List<String> ids) {
        return genreRepository.findGenreByIdIn(ids);
    }

    @Override
    public void deleteGenre(String id) {
        genreRepository.deleteById(id);
    }
}
