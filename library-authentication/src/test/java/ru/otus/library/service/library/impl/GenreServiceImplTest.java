package ru.otus.library.service.library.impl;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ru.otus.library.data.library.model.Genre;
import ru.otus.library.repository.GenreRepository;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class GenreServiceImplTest {

    @Mock
    private GenreRepository genreRepository;

    @InjectMocks
    private GenreServiceImpl genreService;


    @Test
    public void deleteGenreTest() {
        final Long id = 4L;
        genreService.deleteGenre(id);
        verify(genreRepository, times(1)).deleteById(id);
    }

    @Test
    public void saveGenreTest() {
        final Long id = 4L;
        Genre genre = new Genre();
        genre.setName("DFg");
        genre.setGenreId(id);
        genreService.saveGenre(genre);
        verify(genreRepository, times(1)).save(genre);
    }

    @Test
    public void updateGenreTest() {
        final Long id = 4L;
        Genre genre = new Genre();
        genre.setName("DFg");
        genre.setGenreId(id);
        genreService.updateGenre(genre);
        verify(genreRepository, times(1)).save(genre);
    }

    @Test
    public void getGenreByIdTest() {
        final long id = 1;
        genreService.getGenreById(id);
        verify(genreRepository, times(1)).findById(id);
    }

    @Test
    public void findAllTest() {
        genreService.findAllGenre();
        verify(genreRepository, times(1)).findAll();
    }



}