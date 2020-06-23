package ru.otus.library.service.library.impl;

import org.springframework.stereotype.Service;
import ru.otus.library.data.library.model.Genre;
import ru.otus.library.repository.GenreRepository;
import ru.otus.library.service.library.GenreService;

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
        genreRepository.save(genre);
    }


    @Override
    public List<Genre> findAllGenre() {
        return genreRepository.findAll();
    }

    @Override
    public void deleteGenre(Long id) {
        genreRepository.deleteById(id);
    }

    @Override
    public Optional<Genre> getGenreById(Long id) {
        return genreRepository.findById(id);
    }

    @Override
    public void updateGenre(Genre genre) {
        genreRepository.save(genre);
    }
}

