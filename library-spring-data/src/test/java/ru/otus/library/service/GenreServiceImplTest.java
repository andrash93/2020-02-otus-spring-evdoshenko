package ru.otus.library.service;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ru.otus.library.model.Genre;
import ru.otus.library.repository.GenreRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
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
    public void findGenreByName() {

        List<Genre> genreByName = genreService.findGenreByName("");
        assertEquals(0, genreByName.size());

        List<Genre> genres = new ArrayList<>();
        genres.add(new Genre());

        String genreName = "жанр";
        when(genreRepository.findByName(genreName)).thenReturn(genres);

        List<Genre> resultList = genreService.findGenreByName(genreName);

        assertEquals(1, resultList.size());
        verify(genreRepository, times(1)).findByName(genreName);
    }


    @Test
    public void deleteBook() {
        final long id = 1;
        genreService.deleteGenre(id);
        verify(genreRepository, times(1)).deleteById(id);
    }


}