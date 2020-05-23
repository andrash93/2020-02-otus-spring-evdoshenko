package ru.otus.library.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ru.otus.library.model.Author;
import ru.otus.library.repository.AuthorRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthorServiceImplTest {
    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorServiceImpl authorService;

    @Test
    public void findAuthorByNameTest() {

        List<Author> authorsByName = authorService.findAuthorsByName("");
        assertEquals(0, authorsByName.size());

        List<Author> authors = new ArrayList<>();
        authors.add(new Author());

        String authorName = "автор";
        when(authorRepository.findByName(authorName)).thenReturn(authors);

        List<Author> resultList = authorService.findAuthorsByName(authorName);

        assertEquals(1, resultList.size());
        verify(authorRepository, times(1)).findByName(authorName);
    }

    @Test
    public void deleteAuthorTest() {
        final String id = "5eaf2e4b8e7c4112fd80d972";
        authorService.deleteAuthor(id);
        verify(authorRepository, times(1)).deleteById(id);
    }
}