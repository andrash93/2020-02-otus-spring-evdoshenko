package ru.otus.library.service;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ru.otus.library.repository.BookRepository;
import ru.otus.library.model.Author;
import ru.otus.library.model.Book;
import ru.otus.library.service.impl.BookServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorService authorService;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    public void findBookByNameTest() {

        List<Book> booksByName = bookService.findBooksByName("");
        assertEquals(0, booksByName.size());

        List<Book> books = new ArrayList<>();
        books.add(new Book());

        String bookName = "книга";
        when(bookRepository.findBooksByName(bookName)).thenReturn(books);

        List<Book> resultList = bookService.findBooksByName(bookName);

        assertEquals(1, resultList.size());
        verify(bookRepository, times(1)).findBooksByName(bookName);
    }

    @Test
    public void findBookByAuthorNameTest() {

        String authorName = "автор";

        List<Book> books = new ArrayList<>();
        books.add(new Book());
        books.add(new Book());

        List<Author> authors = new ArrayList<>();
        Author author = new Author();
        author.setName(authorName);
        author.setBooks(books);
        authors.add(author);


        when(authorService.findAuthorsByName(authorName)).thenReturn(authors);

        List<Book> resultList = bookService.findBooksByAuthor(authorName);

        assertEquals(2, resultList.size());
        verify(authorService, times(1)).findAuthorsByName(authorName);
    }

    @Test
    public void deleteAuthorTest() {
        final long id = 1;
        bookService.deleteBook(id);
        verify(bookRepository, times(1)).deleteBook(id);
    }

}