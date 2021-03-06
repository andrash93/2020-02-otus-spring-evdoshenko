package ru.otus.library.service;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ru.otus.library.repository.AuthorRepository;
import ru.otus.library.model.Author;
import ru.otus.library.service.impl.AuthorServiceImpl;

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
        when(authorRepository.findAuthorsByName(authorName)).thenReturn(authors);

        List<Author> resultList = authorService.findAuthorsByName(authorName);

        assertEquals(1, resultList.size());
        verify(authorRepository, times(1)).findAuthorsByName(authorName);
    }

    @Test
    public void deleteAuthorTest() {
        final long id = 1;
        authorService.deleteAuthor(id);
        verify(authorRepository, times(1)).deleteAuthor(id);
    }

}