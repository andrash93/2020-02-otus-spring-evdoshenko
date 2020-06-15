package ru.otus.library.service.impl;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ru.otus.library.data.model.Genre;
import ru.otus.library.repository.GenreRepository;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GenreServiceImplTest {

    @Mock
    private GenreRepository genreRepository;

    @InjectMocks
    private GenreServiceImpl genreService;


    @Test
    public void deleteGenreTest() {
        final String id = "5eaf2e4b8e7c4112fd80d972";
        genreService.deleteGenre(id);
        verify(genreRepository, times(1)).deleteById(id);
    }

    @Test
    public void saveGenreTest() {
        final String id = "5eaf2e4b8e7c4112fd80d972";
        Genre genre = new Genre();
        genre.setName("DFg");
        genre.setId(id);
        genreService.saveGenre(genre);
        verify(genreRepository, times(1)).insert(genre);
    }

    @Test
    public void updateGenreTest() {
        final String id = "5eaf2e4b8e7c4112fd80d972";
        Genre genre = new Genre();
        genre.setName("DFg");
        genre.setId(id);
        when(genreRepository.existsById(id)).thenReturn(true);

        genreService.updateGenre(id,genre);
        verify(genreRepository, times(1)).save(genre);
    }

    @Test
    public void getGenreByIdsTest() {
        List<String> authorIds = Arrays.asList("5eaf2e4b8e7c4112fd80d972", "5eaf2e4b8e7c4112fd80d972");
        genreService.getGenreByIds(authorIds);
        verify(genreRepository, times(1)).findGenreByIdIn(authorIds);
    }


}